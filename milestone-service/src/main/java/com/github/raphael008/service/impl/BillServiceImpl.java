package com.github.raphael008.service.impl;

import com.github.raphael008.mapper.BaseMapper;
import com.github.raphael008.mapper.BillMapper;
import com.github.raphael008.model.Bill;
import com.github.raphael008.model.BillExample;
import com.github.raphael008.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("billService")
public class BillServiceImpl extends BaseServiceImpl<Bill, BillExample, Long> implements BillService {
    @Autowired
    private BillMapper billMapper;

    protected BaseMapper getMapper() {
        return billMapper;
    }
}