package com.github.raphael008.manager.impl;

import com.github.raphael008.manager.BillManager;
import com.github.raphael008.model.Bill;
import com.github.raphael008.model.BillDetail;
import com.github.raphael008.service.BillDetailService;
import com.github.raphael008.service.BillService;
import com.github.raphael008.vo.BillRequestVO;
import com.github.raphael008.vo.BillResponseVO;
import net.sourceforge.tess4j.Tesseract;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillManagerImpl implements BillManager {

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
        bill.setCreatorId(0L);
        bill.setCreateTime(new Date());
        billService.insert(bill);

        List<BillDetail> billDetails = new ArrayList<>();
        for (Long passenger : passengers) {
            BillDetail billDetail = new BillDetail();
            billDetail.setBillId(bill.getBillId());
            billDetail.setUserId(passenger);
            billDetail.setBillDetailPrice(averagePrice);
            billDetails.add(billDetail);
        }
        billDetailService.insertRange(billDetails);

        System.out.println();
    }

    @Override
    public BillRequestVO averageBillForDiDiByImage(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest formData = (MultipartHttpServletRequest) request;
        MultipartFile file = formData.getFile("file");
        String[] passengers = formData.getParameterValues("passenger");

        String filePath = "C:\\Users\\Meow\\Desktop\\Image\\" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssuuu") + ".png";
        file.transferTo(new File(filePath));

        File image = new File(filePath);
        BufferedImage bufferedImage = ImageIO.read(image);
        BufferedImage subimage = bufferedImage.getSubimage(780, 850, 110, 100);

        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("eng");
        String price = tesseract.doOCR(subimage);

        price = price.replaceAll("\n", "");

        BillRequestVO billRequestVO = new BillRequestVO();
        billRequestVO.setBillDate(new Date());
        billRequestVO.setBillPrice(new BigDecimal(price));
        billRequestVO.setPassengers(Arrays.stream(passengers).map(Long::new).collect(Collectors.toList()));
        averageBillForDiDi(billRequestVO);
        return billRequestVO;
    }
}
