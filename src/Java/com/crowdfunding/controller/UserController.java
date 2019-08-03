package com.crowdfunding.controller;

import com.crowdfunding.model.Password;
import com.crowdfunding.model.Role;
import com.crowdfunding.model.User;
import com.crowdfunding.repository.RoleRepository;
import com.crowdfunding.repository.UserRepository;
import com.crowdfunding.service.SecurityServiceImplementation;
import com.crowdfunding.service.UserServiceImplementation;
import com.crowdfunding.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordValidator passwordValidator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImplementation userServiceImplementation;

    @Autowired
    SecurityServiceImplementation securityServiceImplementation;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String login(Model model, String error, String logout, String message) {
        if(error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if(logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        if(message != null) {
            model.addAttribute("message", message);
        }

        return "/login";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getList(Model model) {
        if(userServiceImplementation.getCurrentUser() == null) {
            model.addAttribute("message", "You need to log in.");
            return "redirect:http://localhost:8087/login";
        }

        List<User> users = new ArrayList<>(userRepository.findAll());
        model.addAttribute("users", users);

        return "users";
    }

    @RequestMapping(value = "/users/id={id}", method = RequestMethod.GET)
    public String getUserProfile(@PathVariable int id, String warning, String message, String error, Model model){
        if(userServiceImplementation.getCurrentUser() == null) {
            model.addAttribute("message", "You need to log in.");
            return "redirect:http://localhost:8087/login";
        }

        User user = userRepository.findById(id);
        model.addAttribute("user", user);

        if(warning != null) {
            model.addAttribute("warning", warning);
        }

        if(message != null) {
            model.addAttribute("message", message);
        }

        if(error != null) {
            model.addAttribute("error", error);
        }

        Role userRole = userServiceImplementation.getRoleByUsername(user.getUsername());

        Role role = userServiceImplementation.getCurrentUserRole();
        if (role.getId() == 3L) {
            model.addAttribute("adminRole", userRole);
            model.addAttribute("editPassword", new Password());
        }
        if (userServiceImplementation.isCurrentUser(id)) {
            return "redirect:http://localhost:8087/profile";
        }
        if (role.getId() == 1L || role.getId() == 4L) {
            model.addAttribute("noRole", role);
        }

        return "/users/id";
    }

    @RequestMapping(value = "/users/id={id}", method = RequestMethod.POST)
    public String actionsWithUser(@ModelAttribute("editPassword") Password editPassword, Model model,
                                  @PathVariable int id) {
        User user = userRepository.findById(id);

        if (editPassword.getPassword() != null) {
            if (passwordValidator.hasErrors(editPassword, model)) {
                return "redirect:http://localhost:8087/users/id=" + id ;
            }

            userServiceImplementation.updatePassword(user, editPassword);
            model.addAttribute("message", "The password is updated");
            return "redirect:http://localhost:8087/users/id=" + id ;
        }

        Role role = userServiceImplementation.getRoleByUsername(user.getUsername());

        if (role.getId() == 2 || role.getId() == 1) {
            userServiceImplementation.blockUser(user);
            model.addAttribute("warning", "The user is blocked");
            return "redirect:http://localhost:8087/users/id=" + id ;
        }

        if (role.getId() == 4) {
            userServiceImplementation.unblockUser(user);
            model.addAttribute("warning", "The user is unblocked");
            return "redirect:http://localhost:8087/users/id=" + id ;
        }

        return "redirect:http://localhost:8087/users/id=" + id ;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getMyProfile(Model model, String error, String message) {
        if(userServiceImplementation.getCurrentUser() == null) {
            model.addAttribute("message", "You need to log in.");
            return "redirect:http://localhost:8087/login";
        }

        model.addAttribute("newPassword", new Password());

        if(message != null) {
            model.addAttribute("message", "Password successfully updated.");
        }

        if(error != null) {
            model.addAttribute("error", "Passwords don't match.");
        }

        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String postProfile(@ModelAttribute("newPassword") Password newPassword, BindingResult bindingResult,
                              Model model) {
        passwordValidator.validate(newPassword, bindingResult);

        if (bindingResult.hasErrors()) {
            return "profile";
        }

        userServiceImplementation.updatePassword(newPassword);
        model.addAttribute("message", "Password successfully updated.");

        return "redirect:http://localhost:8087/profile";
    }
}

