package com.crowdfunding.service;

import com.crowdfunding.model.Password;
import com.crowdfunding.model.Role;
import com.crowdfunding.model.User;

public interface UserService {

    void updatePassword(Password newPassword);

    User findByUsername(String username);

    boolean addUser(User user);

    boolean activateUser(String code);

    User findByActivationCode(String code);

    Role getCurrentUserRole();

    boolean isCurrentUser(int id);

    void blockUser(User user);

    void unblockUser(User user);

    boolean hasDifferences(User user, User editUser);

    void update(User user, User editUser);
}
