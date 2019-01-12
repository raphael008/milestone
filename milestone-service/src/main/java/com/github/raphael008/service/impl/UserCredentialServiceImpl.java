package com.github.raphael008.service.impl;

import com.github.raphael008.mapper.BaseMapper;
import com.github.raphael008.mapper.UserCredentialMapper;
import com.github.raphael008.model.UserCredential;
import com.github.raphael008.model.UserCredentialExample;
import com.github.raphael008.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userCredentialService")
public class UserCredentialServiceImpl extends BaseServiceImpl<UserCredential, UserCredentialExample, Long> implements UserCredentialService {
    @Autowired
    private UserCredentialMapper userCredentialMapper;

    protected BaseMapper getMapper() {
        return userCredentialMapper;
    }

    @Override
    public UserCredential findByUserId(Long userId) {
        return userCredentialMapper.findByUserId(userId);
    }
}