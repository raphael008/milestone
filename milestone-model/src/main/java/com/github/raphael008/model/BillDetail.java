package com.github.raphael008.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDetail {
    private Long billDetailId;

    private Long billId;

    private Long userId;

    private BigDecimal billDetailPrice;
}