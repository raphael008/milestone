package com.github.raphael008.controller;

import com.github.raphael008.manager.LoginManager;
import com.github.raphael008.vo.LoginRequestVO;
import com.github.raphael008.vo.LoginResponseVO;
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
    public LoginResponseVO login(@RequestBody LoginRequestVO userRequestVO) {
        LoginResponseVO userResponseVO = loginManager.login(userRequestVO.getUsername(), userRequestVO.getPassword());
        return userResponseVO;
    }
}
