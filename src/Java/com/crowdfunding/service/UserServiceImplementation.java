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
    public Role getCurrentUserRole() {
        String currentUsername = securityServiceImplementation.findLoggedInUsername().getUsername();
        if (userRepository.findByUsername(currentUsername) != null) {
            return roleRepository.findById(userRoleRepository.findByUserId(userRepository.
                    findByUsername(currentUsername).getId()).getRoleId());
        }
        return null;
    }

    @Override
    public boolean isCurrentUser(int id) {
        String currentUsername = securityServiceImplementation.findLoggedInUsername().getUsername();
        User currentUser = userRepository.findByUsername(currentUsername);
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
        roles.add(roleRepository.getOne(2L));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public boolean hasDifferences(User user, User editUser) {
        if (editUser.getUsername() != null) {
            return !user.getUsername().equals(editUser.getUsername());
        }
        if (editUser.getPassword() != null) {
            return !user.getPassword().equals(editUser.getPassword());
        }
        if (editUser.getEmail() != null) {
            return !user.getEmail().equals(editUser.getEmail());
        }

        return false;
    }

    @Override
    public void update(User user, User editUser) {
        if (editUser.getUsername() != null) {
            user.setUsername(editUser.getUsername());
        }
        if (editUser.getPassword() != null) {
            user.setPassword(editUser.getPassword());
        }
        if (editUser.getEmail() != null) {
            user.setEmail(editUser.getEmail());
        }

        userRepository.save(user);
    }

    @Override
    public void updatePassword(Password newPassword) throws UsernameNotFoundException{
        String currentUsername = securityServiceImplementation.findLoggedInUsername().getUsername();
        User currentUser = userRepository.findByUsername(currentUsername);
        if (currentUser!=null) {
            currentUser.setPassword(bCryptPasswordEncoder.encode(newPassword.getPassword()));
            userRepository.save(currentUser);
        }
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
