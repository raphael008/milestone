package com.github.raphael008.controller;

import com.github.raphael008.manager.LoginManager;
import com.github.raphael008.manager.impl.LoginManagerImpl;
import com.github.raphael008.vo.UserRequestVO;
import com.github.raphael008.vo.UserResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="web", produces = "application/json")
public class WebController {

    @Autowired
    private LoginManager loginManager;

    @PostMapping("login")
    public UserResponseVO login(@RequestBody UserRequestVO userRequestVO) {
        UserResponseVO userResponseVO = loginManager.login(userRequestVO.getUsername(), userRequestVO.getPassword());
        return userResponseVO;
    }
}
