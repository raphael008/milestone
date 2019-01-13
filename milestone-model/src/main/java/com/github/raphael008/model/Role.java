package com.github.raphael008.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {
    private Long roleId;

    private String roleName;

    private Long creatorId;

    private Integer deleted;
}