package com.github.raphael008.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Bill {
    private Long billId;

    private Date billDate;

    private BigDecimal billPrice;

    private String billImage;

    private String remarks;

    private Long creatorId;

    private Date createTime;
}