package com.github.raphael008.manager.impl;

import com.github.raphael008.enums.DeletedStatus;
import com.github.raphael008.manager.BillManager;
import com.github.raphael008.model.Bill;
import com.github.raphael008.model.BillDetail;
import com.github.raphael008.service.BillDetailService;
import com.github.raphael008.service.BillService;
import com.github.raphael008.vo.BillRequestVO;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BillManagerImpl implements BillManager {

    @Value("${path.image}")
    private String uploadPath;

    @Autowired
    private BillService billService;

    @Autowired
    private BillDetailService billDetailService;

    @Transactional
    @Override
    public void averageBillForDiDi(BillRequestVO billRequestVO) {
        if (Objects.isNull(billRequestVO)) {
            throw new RuntimeException();
        }

        BigDecimal billPrice = billRequestVO.getBillPrice();
        if (Objects.isNull(billPrice)) {
            throw new RuntimeException();
        }

        List<Long> passengers = billRequestVO.getPassengers();
        if (CollectionUtils.isEmpty(passengers)) {
            throw new RuntimeException();
        }

        int passengerCount = passengers.size();
        BigDecimal averagePrice = billPrice.divide(new BigDecimal(passengerCount), 2, RoundingMode.HALF_EVEN);

        Bill bill = new Bill();
        bill.setBillDate(billRequestVO.getBillDate());
        bill.setBillPrice(billPrice);
        bill.setBillImage(billRequestVO.getBillImage());
        bill.setRemarks(billRequestVO.getRemarks());
        bill.setCreatorId(0L);
        bill.setCreateTime(new Date());
        bill.setDeleted(DeletedStatus.NO.getIndex());
        billService.insert(bill);

        List<BillDetail> billDetails = new ArrayList<>();
        for (Long passenger : passengers) {
            BillDetail billDetail = new BillDetail();
            billDetail.setBillId(bill.getBillId());
            billDetail.setUserId(passenger);
            billDetail.setBillDetailPrice(averagePrice);
            billDetail.setDeleted(DeletedStatus.NO.getIndex());
            billDetails.add(billDetail);
        }
        billDetailService.insertRange(billDetails);

        System.out.println();
    }

    @Override
    public BillRequestVO averageBillForDiDiByImage(HttpServletRequest request) {
        MultipartHttpServletRequest formData = (MultipartHttpServletRequest) request;
        Date billDate = null;
        try {
            billDate = DateUtils.parseDate(formData.getParameter("billDate"), "yyyy-MM-dd");
        } catch (ParseException e) {
            throw new RuntimeException("解析日期失败");
        }
        MultipartFile file = formData.getFile("file");
        String[] passengers = formData.getParameterValues("passenger");
        String remarks = formData.getParameter("remarks");

        String currentTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssuuu");
        String fileName = String.format("%s.png", currentTime);
        String filePath = Paths.get(uploadPath, fileName).toString();
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败");
        }

        File image = new File(filePath);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(image);
        } catch (IOException e) {
            throw new RuntimeException("读取图片失败");
        }
        BufferedImage subimage = bufferedImage.getSubimage(780, 850, 110, 100);
        String subFileName = String.format("%s - sub.png", currentTime);
        String subFilePath = Paths.get(uploadPath, subFileName).toString();
        try {
            ImageIO.write(subimage, "PNG", new File(subFilePath));
        } catch (IOException e) {
            throw new RuntimeException("保存图片失败");
        }

        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("eng");
        String price = null;
        try {
            price = tesseract.doOCR(subimage);
        } catch (TesseractException e) {
            throw new RuntimeException("图像识别失败");
        }

        log.info("{}", price);

        price = price.replaceAll("\n", "");

        BillRequestVO billRequestVO = new BillRequestVO();
        billRequestVO.setBillDate(billDate);
        billRequestVO.setBillPrice(new BigDecimal(price));
        billRequestVO.setBillImage(fileName);
        billRequestVO.setRemarks(remarks);
        billRequestVO.setPassengers(Arrays.stream(passengers).map(Long::new).collect(Collectors.toList()));
        averageBillForDiDi(billRequestVO);
        return billRequestVO;
    }
}
