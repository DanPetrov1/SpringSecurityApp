package com.crowdfunding.controller;

import com.crowdfunding.model.Password;
import com.crowdfunding.model.User;
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
    @ResponseStatus(value = HttpStatus.OK)
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String admin(Model model) {
        return "admin";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> getAllUsers(){
        return new ArrayList<>(userRepository.findAll());
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteItem(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(Model model, String error, String message) {
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

