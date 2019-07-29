package com.crowdfunding.controller;

import com.crowdfunding.model.User;
import com.crowdfunding.repository.UserRepository;
import com.crowdfunding.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImplementation userServiceImplementation;

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

    @GetMapping(value = "/profile")
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView getProfile(User user) {
        ModelAndView result = new ModelAndView("profile");

        result.addObject("user", user);

        return result;
    }

    @PostMapping(value = "/profile")
    @ResponseStatus(value = HttpStatus.OK)
    public String postProfile(@RequestParam(name = "username") String username,
                              @RequestParam(name = "password", required = false) String password) {
        User user = userServiceImplementation.findByUsername(username);

        if (!username.isEmpty()) {
            user.setUsername(username);
        }

        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }

        userRepository.save(user);

        return "redirect:/profile";
    }
}

