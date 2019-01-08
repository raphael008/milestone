package com.github.raphael008.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BillDetail {
    private Long billDetailId;

    private Long billId;

    private Long userId;

    private BigDecimal billDetailPrice;
}