package com.github.raphael008.commons;

import com.github.raphael008.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class LoginContext {

    private static ThreadLocal<LoginContext> threadLocal = new ThreadLocal<>();

    private User user;

    public static LoginContext getContext() {
        LoginContext loginContext = threadLocal.get();
        if (Objects.isNull(loginContext)) {
            return null;
        } else {
            return loginContext;
        }
    }

    public static void setContext(LoginContext loginContext) {
        threadLocal.set(loginContext);
    }
}
