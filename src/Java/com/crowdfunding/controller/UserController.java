package com.crowdfunding.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class UserController {

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String login(Model model, String error, String logout) {
        if(error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if(logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
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
}

