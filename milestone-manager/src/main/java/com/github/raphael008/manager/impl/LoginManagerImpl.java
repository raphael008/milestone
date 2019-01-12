package com.github.raphael008.manager.impl;

import com.github.raphael008.manager.LoginManager;
import com.github.raphael008.model.User;
import com.github.raphael008.model.UserCredential;
import com.github.raphael008.service.UserCredentialService;
import com.github.raphael008.service.UserService;
import com.github.raphael008.vo.LoginResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginManagerImpl implements LoginManager {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCredentialService userCredentialService;

    @Override
    public LoginResponseVO login(String username, String password) {
        User user = userService.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new RuntimeException("登陆失败，此用户不存在。");
        }

        UserCredential userCredential = userCredentialService.findByUserId(user.getUserId());
        return null;
    }
}
