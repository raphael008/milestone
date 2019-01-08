package com.github.raphael008.manager.impl;

import com.github.raphael008.manager.BillManager;
import com.github.raphael008.model.Bill;
import com.github.raphael008.model.BillDetail;
import com.github.raphael008.service.BillDetailService;
import com.github.raphael008.service.BillService;
import com.github.raphael008.vo.BillRequestVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        billService.insert(bill);

        List<BillDetail> billDetails = new ArrayList<>();
        for (Long passenger : passengers) {
            BillDetail billDetail = new BillDetail();
            billDetail.setBillId(bill.getBillId());
            billDetail.setUserId(passenger);
            billDetail.setBillDetailPrice(averagePrice);
            billDetails.add(billDetail);
        }
    }
}
