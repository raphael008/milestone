package com.github.raphael008.commons;

import java.util.Objects;

public class LoginContext {
    private static ThreadLocal<LoginContext> threadLocal = new ThreadLocal<>();

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
