package com.github.raphael008.manager.impl;

import com.github.raphael008.manager.UserManager;
import com.github.raphael008.model.User;
import com.github.raphael008.model.UserCredential;
import com.github.raphael008.service.UserCredentialService;
import com.github.raphael008.service.UserService;
import com.github.raphael008.utils.ReflectionUtil;
import com.github.raphael008.vo.UserRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCredentialService userCredentialService;

    @Override
    public Map addUser(UserRequestVO userRequestVO) {
        SecureRandom random = new SecureRandom();
        String userSalt = BCrypt.gensalt(15, random);

        User user = new User();
        ReflectionUtil.copySameField(userRequestVO, user);
        user.setUserSalt(userSalt);
        user.setCreatorId(0L);
        userService.insertSelective(user);

        Long userId = user.getUserId();
        String userName = userRequestVO.getUserName();
        String password = userRequestVO.getPassword();

        String hashedPassword = BCrypt.hashpw(password, userSalt);

        UserCredential credential = new UserCredential();
        credential.setUserId(userId);
        credential.setUserPassword(hashedPassword);
        userCredentialService.insert(credential);

        Map<String, String> userMap = new HashMap<>();
        userMap.put("Username", userName);
        userMap.put("Password", password);
        return userMap;
    }
}
