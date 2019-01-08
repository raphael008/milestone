package com.github.raphael008.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long userId;

    private String userName;

    private String userNickname;

    private String userEmail;

    private Integer userAge;

    private String userAvatar;

    private Integer userGender;

    private String userSalt;

    private Long creatorId;

    private Integer blocked;
}