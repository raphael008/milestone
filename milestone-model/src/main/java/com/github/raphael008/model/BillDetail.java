package com.github.raphael008.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDetail {
    private Long billDetailId;

    private Long billId;

    private Long userId;
}