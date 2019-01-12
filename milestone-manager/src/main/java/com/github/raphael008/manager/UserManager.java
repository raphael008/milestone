package com.github.raphael008.manager;

import com.github.raphael008.vo.UserRequestVO;

import java.util.Map;

public interface UserManager {
    Map addUser(UserRequestVO userRequestVO);
}
