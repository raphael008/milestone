package com.github.raphael008.service.impl;

import com.github.raphael008.mapper.BaseMapper;
import com.github.raphael008.mapper.UserMapper;
import com.github.raphael008.model.User;
import com.github.raphael008.model.UserCredential;
import com.github.raphael008.model.UserExample;
import com.github.raphael008.service.UserCredentialService;
import com.github.raphael008.service.UserService;
import com.github.raphael008.utils.ReflectionUtil;
import com.github.raphael008.vo.UserRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, UserExample, Long> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCredentialService userCredentialService;

    protected BaseMapper getMapper() {
        return userMapper;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}