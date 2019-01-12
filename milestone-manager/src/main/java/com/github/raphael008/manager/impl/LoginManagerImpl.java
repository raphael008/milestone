package com.github.raphael008.manager.impl;

import com.github.raphael008.commons.LoginContext;
import com.github.raphael008.manager.LoginManager;
import com.github.raphael008.model.User;
import com.github.raphael008.model.UserCredential;
import com.github.raphael008.service.UserCredentialService;
import com.github.raphael008.service.UserService;
import com.github.raphael008.utils.ReflectionUtil;
import com.github.raphael008.vo.LoginResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Service
public class LoginManagerImpl implements LoginManager {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCredentialService userCredentialService;

    @Override
    public LoginResponseVO login(String username, String password) {
        User user = userService.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new RuntimeException("登录失败，此用户不存在。");
        }

        UserCredential userCredential = userCredentialService.findByUserId(user.getUserId());
        if (Objects.isNull(userCredential)) {
            throw new RuntimeException("内部错误：0001，请联系管理员。");
        }

        String userSalt = user.getUserSalt();
        String hashPassword = BCrypt.hashpw(password, userSalt);
        String userPassword = userCredential.getUserPassword();

        if (userPassword.equals(hashPassword)) {
            LoginContext context = new LoginContext();
            context.setUser(user);

            session.setAttribute("context", context);

            LoginResponseVO loginResponseVO = new LoginResponseVO();
            ReflectionUtil.copySameField(user, loginResponseVO);
            return loginResponseVO;
        } else {
            throw new RuntimeException("登录失败，密码错误。");
        }
    }
}
