package com.github.raphael008.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseVO {
    private Long userId;

    private String userNickname;

    private String userAvatar;
}
