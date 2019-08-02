package com.crowdfunding.controller;

import com.crowdfunding.model.Post;
import com.crowdfunding.model.Topic;
import com.crowdfunding.repository.PostRepository;
import com.crowdfunding.service.PostServiceImplementation;
import com.crowdfunding.validator.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @Autowired
    private PostServiceImplementation postServiceImplementation;

    @Autowired
    private PostValidator postValidator;

    @Autowired
    private PostRepository postRepository;

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String welcome(Model model, String error, String message) {
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("newPost", new Post());
        model.addAttribute("topicName", new Topic());

        if (error != null) {
            model.addAttribute("error", error);
        }

        if(message != null) {
            model.addAttribute("message", message);
        }

        return "feed";
    }

    @RequestMapping(value = "/feed", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("newPost") Post post , @ModelAttribute("topicName") Topic topic,
                          BindingResult bindingResult, Model model) {
        if (post != null) {
            if (topic.getTopicName().length() < 1) {
                model.addAttribute("error", "Write the name of the topic.");
                return "feed";
            }

            postValidator.validate(post, bindingResult);
            if (bindingResult.hasErrors()) {
                return "feed";
            }

            postServiceImplementation.createPost(post, topic.getTopicName());
        }

        return "redirect:localhost:8087/feed";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }
}
