package com.github.raphael008.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserRequestVO {
    private String userName;

    private String password;

    private String userNickname;

    private String userEmail;

    private Integer userAge;

    private String userAvatar;

    private Integer userGender;
}
