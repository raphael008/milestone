package com.github.raphael008.controller;

import com.github.raphael008.controller.impl.BaseControllerImpl;
import com.github.raphael008.manager.UserManager;
import com.github.raphael008.model.User;
import com.github.raphael008.model.UserExample;
import com.github.raphael008.service.BaseService;
import com.github.raphael008.service.UserService;
import com.github.raphael008.vo.UserRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value="user", produces = "application/json")
public class UserController extends BaseControllerImpl<User, UserExample, Long> {
    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    protected BaseService getService() {
        return userService;
    }

    @PostMapping("addUser")
    public Map addUser(@RequestBody UserRequestVO userRequestVO) {
        Map map = userManager.addUser(userRequestVO);
        return map;
    }
}