package com.github.raphael008.service;

import com.github.raphael008.model.User;
import com.github.raphael008.model.UserExample;
import com.github.raphael008.vo.UserRequestVO;

import java.util.Map;

public interface UserService extends BaseService<User, UserExample, Long> {
    User findByUsername(String username);
}