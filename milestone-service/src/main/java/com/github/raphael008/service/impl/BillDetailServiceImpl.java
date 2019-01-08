package com.github.raphael008.service.impl;

import com.github.raphael008.mapper.BaseMapper;
import com.github.raphael008.mapper.BillDetailMapper;
import com.github.raphael008.model.BillDetail;
import com.github.raphael008.model.BillDetailExample;
import com.github.raphael008.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("billDetailService")
public class BillDetailServiceImpl extends BaseServiceImpl<BillDetail, BillDetailExample, Long> implements BillDetailService {
    @Autowired
    private BillDetailMapper billDetailMapper;

    protected BaseMapper getMapper() {
        return billDetailMapper;
    }
}