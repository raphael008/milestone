package com.github.raphael008.controller;

import com.github.raphael008.auth.Auth;
import com.github.raphael008.controller.impl.BaseControllerImpl;
import com.github.raphael008.manager.BillManager;
import com.github.raphael008.model.Bill;
import com.github.raphael008.model.BillExample;
import com.github.raphael008.service.BaseService;
import com.github.raphael008.service.BillService;
import com.github.raphael008.vo.BillRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value="bill", produces = "application/json")
public class BillController extends BaseControllerImpl<Bill, BillExample, Long> {
    @Autowired
    private BillService billService;

    @Autowired
    private BillManager billManager;

    protected BaseService getService() {
        return billService;
    }

    @PostMapping("averageBillForDiDiByImage")
    public BillRequestVO averageBillForDiDiByImage(HttpServletRequest request) {
        try {
            BillRequestVO billRequestVO = billManager.averageBillForDiDiByImage(request);
            return billRequestVO;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}