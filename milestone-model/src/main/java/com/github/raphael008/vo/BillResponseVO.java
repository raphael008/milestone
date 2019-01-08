package com.github.raphael008.vo;

import com.github.raphael008.model.Bill;
import com.github.raphael008.model.BillDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BillResponseVO extends Bill {
    List<BillDetail> billDetails;
}
