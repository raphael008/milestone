package com.github.raphael008.manager;

import com.github.raphael008.vo.BillRequestVO;
import com.github.raphael008.vo.BillResponseVO;

import javax.servlet.http.HttpServletRequest;

public interface BillManager {

    void averageBillForDiDi(BillRequestVO billRequestVO);

    BillRequestVO averageBillForDiDiByImage(HttpServletRequest request) throws Exception;
}
