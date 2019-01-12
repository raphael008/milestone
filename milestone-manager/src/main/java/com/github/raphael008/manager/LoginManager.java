package com.github.raphael008.manager;

import com.github.raphael008.enums.LoginType;
import com.github.raphael008.vo.LoginResponseVO;

import javax.servlet.http.HttpServletRequest;

public interface LoginManager {
    LoginResponseVO login(String username, String password);

    Boolean checkAuth(HttpServletRequest request, LoginType loginType);

    Boolean checkAuthForWeb(HttpServletRequest request);
}
