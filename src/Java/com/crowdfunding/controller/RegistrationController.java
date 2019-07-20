package com.crowdfunding.controller;

import com.crowdfunding.model.User;
import com.crowdfunding.service.SecurityService;
import com.crowdfunding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/registration")
    @ResponseStatus(value = HttpStatus.OK)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    @ResponseStatus(value = HttpStatus.OK)
    public String addUser(User user, Map<String, Object> model) {
        if (!userService.addUser(user)) {
            model.put("message", "User exists!");
            return "registration";
        }

        return "redirect:login";
    }

    @GetMapping("/activate/{code}")
    @ResponseStatus(value = HttpStatus.OK)
    public String activate(Model model, @PathVariable String code) {
        User user = userService.findByActivationCode(code);

        boolean isActivated = userService.activateUser(code);

        if (!isActivated) {
            model.addAttribute("message", "Activation code is not found!");
        }

        model.addAttribute("message", "User successfully activated");

        //securityService.autoLogin(user.getUsername(), user.getConfirmPassword());

        return "welcome";
    }
}
