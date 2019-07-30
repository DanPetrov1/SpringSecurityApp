package com.crowdfunding.controller;

import com.crowdfunding.model.User;
import com.crowdfunding.service.UserService;
import com.crowdfunding.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.addUser(userForm);

        return "redirect:http://localhost:8087/login";
    }

    @RequestMapping(value = "/activate/{code}", method = RequestMethod.GET)
    public ModelAndView activate(Model model, @PathVariable String code)
    {

        boolean isActivated = userService.activateUser(code);

        if (!isActivated) {
            model.addAttribute("message", "Activation code is not found!");
        }

        model.addAttribute("message", "User successfully activated");

        return new ModelAndView("redirect:http://localhost:8087/login");
    }
}
