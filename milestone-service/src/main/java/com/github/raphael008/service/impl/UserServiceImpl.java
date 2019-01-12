package com.github.raphael008.service.impl;

import com.github.raphael008.enums.BlockedStatus;
import com.github.raphael008.mapper.BaseMapper;
import com.github.raphael008.mapper.UserMapper;
import com.github.raphael008.model.User;
import com.github.raphael008.model.UserExample;
import com.github.raphael008.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, UserExample, Long> implements UserService {
    @Autowired
    private UserMapper userMapper;

    protected BaseMapper getMapper() {
        return userMapper;
    }

    @Override
    public User findByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andUserNameEqualTo(username)
                .andBlockedEqualTo(BlockedStatus.NO.getIndex());

        List<User> users = selectByExample(userExample);

        if (users.size() < 1) {
            return null;
        }
        if (users.size() == 1) {
            return users.get(0);
        }
        if (users.size() > 1) {
            throw new RuntimeException("");
        }

        return null;
    }
}