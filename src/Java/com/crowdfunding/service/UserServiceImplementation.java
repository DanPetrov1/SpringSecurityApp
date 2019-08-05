package com.crowdfunding.service;

import com.crowdfunding.model.Password;
import com.crowdfunding.model.Role;
import com.crowdfunding.model.User;
import com.crowdfunding.repository.RoleRepository;
import com.crowdfunding.repository.UserRepository;
import com.crowdfunding.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityServiceImplementation securityServiceImplementation;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean addUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(1L));
        user.setRoles(roles);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCash(0L);

        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s!\n" +
                            "Please, visit next link to activate your account: " +
                            "http://localhost:8087/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }

        return true;
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(2L));
        user.setRoles(roles);
        user.setActivationCode("Activated");
        userRepository.save(user);

        return true;
    }

    @Override
    public User findByActivationCode(String code) {
        return userRepository.findByActivationCode(code);
    }

    @Override
    public Role getRoleByUsername(String username) {
        if (userRepository.findByUsername(username) != null) {
            return roleRepository.findById(userRoleRepository.findByUserId(userRepository.
                    findByUsername(username).getId()).getRoleId());
        }
        return null;
    }

    @Override
    public Role getCurrentUserRole() {
        if (getCurrentUser() != null) {
            return roleRepository.findById((long) userRoleRepository.findByUserId(getCurrentUser().getId()).getRoleId());
        }
        return null;
    }

    @Override
    public boolean isCurrentUser(int id) {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            return currentUser.getId() == id;
        }
        return false;
    }

    @Override
    public void blockUser(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(4L));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void unblockUser(User user) {
        Set<Role> roles = new HashSet<>();
        if (user.getActivationCode().equals("Activated")) {
            roles.add(roleRepository.getOne(2L));
        }
        else {
            roles.add(roleRepository.getOne(1L));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public int getCurrentUserId() {
        User currentUser = getCurrentUser();
        if(currentUser!=null) {
            return currentUser.getId();
        }
        return 0;
    }

    @Override
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User user = securityServiceImplementation.findLoggedInUsername();
        if (user == null) {
            return null;
        } else {
            return userRepository.findByUsername(user.getUsername());
        }
    }

    @Override
    public void replenishCash(int cash) {
        User user = getCurrentUser();

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s!\n" +
                            "You successfully replenished your cash by the %d$.",
                    user.getUsername(), cash
            );

            mailSender.send(user.getEmail(), "Replenishment", message);
        }
        user.setCash(user.getCash() + (long)cash);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(Password newPassword) throws UsernameNotFoundException{
        User currentUser = getCurrentUser();
        if (currentUser!=null) {
            currentUser.setPassword(bCryptPasswordEncoder.encode(newPassword.getPassword()));
            userRepository.save(currentUser);
        }
    }

    @Override
    public void updatePassword(User user, Password newPassword) {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
