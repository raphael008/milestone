package com.github.raphael008.manager;

import com.github.raphael008.vo.LoginResponseVO;

public interface LoginManager {
    LoginResponseVO login(String username, String password);
}
