package com.github.raphael008.auth.manager;

import com.github.raphael008.commons.LoginContext;

public interface WebLoginService {

    LoginContext login(String username, String password);
}
