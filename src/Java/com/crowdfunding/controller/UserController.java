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
            model.addAttribute("message", "User successfully activated.");
        }

        return "/login";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getList(Model model) {
        List<User> users = new ArrayList<>(userRepository.findAll());
        model.addAttribute("users", users);

        return "users";
    }

    @RequestMapping(value = "/users/id={id}", method = RequestMethod.GET)
    public String getUserProfile(@PathVariable int id, Model model){
        User user = userRepository.findById(id);
        model.addAttribute("user", user);

        Role role = userServiceImplementation.getCurrentUserRole();
        if (role.getId() == 3) {
            model.addAttribute("role", role);
        }
        return "/users/id";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getMyProfile(Model model, String error, String message) {
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

