package com.github.raphael008.controller;

import com.github.raphael008.controller.impl.BaseControllerImpl;
import com.github.raphael008.model.BillDetail;
import com.github.raphael008.model.BillDetailExample;
import com.github.raphael008.service.BaseService;
import com.github.raphael008.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("billDetailController")
public class BillDetailController extends BaseControllerImpl<BillDetail, BillDetailExample, Long> {
    @Autowired
    private BillDetailService billDetailService;

    protected BaseService getService() {
        return billDetailService;
    }
}