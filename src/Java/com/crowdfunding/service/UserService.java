package com.crowdfunding.service;

import com.crowdfunding.model.User;

public interface UserService {

    User findByUsername(String username);

    boolean addUser(User user);

    boolean activateUser(String code);

    User findByActivationCode(String code);
}
