package com.github.raphael008.manager;

import com.github.raphael008.vo.UserResponseVO;
import org.springframework.stereotype.Service;

public interface LoginManager {
    UserResponseVO login(String username, String password);
}
