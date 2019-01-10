package com.github.raphael008.controller;

import com.github.raphael008.controller.impl.BaseControllerImpl;
import com.github.raphael008.model.Bill;
import com.github.raphael008.model.BillExample;
import com.github.raphael008.service.BaseService;
import com.github.raphael008.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="bill", produces = "application/json")
public class BillController extends BaseControllerImpl<Bill, BillExample, Long> {
    @Autowired
    private BillService billService;

    protected BaseService getService() {
        return billService;
    }
}