package com.github.raphael008.service.impl;

import com.github.raphael008.mapper.BaseMapper;
import com.github.raphael008.mapper.UserMapper;
import com.github.raphael008.model.User;
import com.github.raphael008.model.UserExample;
import com.github.raphael008.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, UserExample, Long> implements UserService {
    @Autowired
    private UserMapper userMapper;

    protected BaseMapper getMapper() {
        return userMapper;
    }
}