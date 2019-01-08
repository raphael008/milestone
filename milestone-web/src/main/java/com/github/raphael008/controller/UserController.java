package com.github.raphael008.controller;

import com.github.raphael008.controller.impl.BaseControllerImpl;
import com.github.raphael008.model.User;
import com.github.raphael008.model.UserExample;
import com.github.raphael008.service.BaseService;
import com.github.raphael008.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("userController")
public class UserController extends BaseControllerImpl<User, UserExample, Long> {
    @Autowired
    private UserService userService;

    protected BaseService getService() {
        return userService;
    }
}