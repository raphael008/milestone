package com.github.raphael008.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BillRequestVO {

    private Date billDate;

    private BigDecimal billPrice;

    private String billImage;

    private String remarks;

    private List<Long> passengers;
}
