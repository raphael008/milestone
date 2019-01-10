package com.github.raphael008.controller;

import com.github.raphael008.controller.impl.BaseControllerImpl;
import com.github.raphael008.model.UserCredential;
import com.github.raphael008.model.UserCredentialExample;
import com.github.raphael008.service.BaseService;
import com.github.raphael008.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="userCredential", produces = "application/json")
public class UserCredentialController extends BaseControllerImpl<UserCredential, UserCredentialExample, Long> {
    @Autowired
    private UserCredentialService userCredentialService;

    protected BaseService getService() {
        return userCredentialService;
    }
}