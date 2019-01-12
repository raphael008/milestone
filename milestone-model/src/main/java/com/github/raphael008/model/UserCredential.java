package com.github.raphael008.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredential {
    private Long credentialId;

    private Long userId;

    private String userPassword;

    private Integer deleted;
}