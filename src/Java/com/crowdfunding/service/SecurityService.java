package com.crowdfunding.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {

    User findLoggedInUsername();

    void autoLogin(String username, String password);
}
