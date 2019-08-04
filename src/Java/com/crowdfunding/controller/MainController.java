package com.crowdfunding.controller;

import com.crowdfunding.model.Post;
import com.crowdfunding.model.Role;
import com.crowdfunding.model.Topic;
import com.crowdfunding.model.User;
import com.crowdfunding.repository.PostRepository;
import com.crowdfunding.repository.TopicRepository;
import com.crowdfunding.repository.UserRepository;
import com.crowdfunding.service.PostServiceImplementation;
import com.crowdfunding.service.UserServiceImplementation;
import com.crowdfunding.validator.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PostServiceImplementation postServiceImplementation;

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @Autowired
    private PostValidator postValidator;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String welcome(Model model, String error, String message) {
        if(userServiceImplementation.getCurrentUser() == null) {
            model.addAttribute("message", "You need to log in.");
            return "redirect:http://localhost:8087/login";
        }

        if (error != null) {
            model.addAttribute("error", error);
        }

        if(message != null) {
            model.addAttribute("success", message);
        }

        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("newPost", new Post());
        model.addAttribute("topicName", new Topic());
        model.addAttribute("user", userServiceImplementation.getCurrentUser());

        return "feed";
    }

    @RequestMapping(value = "/feed", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("newPost") Post post , @ModelAttribute("topicName") Topic topic,
                          BindingResult bindingResult, Model model) {
        if (post.getMessage() != null) {
            if (topic.getTopicName().length() < 1) {
                model.addAttribute("error", "Write the name of the topic.");
                return "redirect:http://localhost:8087/feed";
            }

            postValidator.validate(post, bindingResult);
            if (bindingResult.hasErrors()) {
                return "/feed";
            }

            postServiceImplementation.createPost(post, topic.getTopicName());
            model.addAttribute("success", "The post has been created successfully.");
            return "redirect:http://localhost:8087/feed";
        }

        model.addAttribute("error", "Write the message.");
        return "redirect:http://localhost:8087/feed";
    }

    @RequestMapping(value = "/topics/id={id}", method = RequestMethod.GET)
    public String getUserProfile(@PathVariable int id, String message, String error, Model model){
        if(userServiceImplementation.getCurrentUser() == null) {
            model.addAttribute("message", "You need to log in.");
            return "redirect:http://localhost:8087/login";
        }

        Topic topic = topicRepository.findById(id);
        model.addAttribute("topic", topic);
        model.addAttribute("posts", postRepository.findAllByIdTopic(topic.getId()));

        Role role = userServiceImplementation.getCurrentUserRole();
        if (role.getId() == 3L || topic.getIdAuthor() == userServiceImplementation.getCurrentUserId()) {
            model.addAttribute("adminRole", role);
        }

        if(message != null) {
            model.addAttribute("message", message);
        }

        if(error != null) {
            model.addAttribute("error", error);
        }

        return "/topics/id";
    }

    @Transactional
    @RequestMapping(value = "/posts/delete={id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") int id, Model model) {
        int topicId = postRepository.findById(id).getIdTopic();
        postRepository.deleteById(id);
        model.addAttribute("message", "Post has been deleted successfully.");
        return "redirect:http://localhost:8087/topics/id=" + topicId;
    }

    @RequestMapping(value = "/topics", method = RequestMethod.GET)
    public String getList(Model model) {
        if(userServiceImplementation.getCurrentUser() == null) {
            model.addAttribute("message", "You need to log in.");
            return "redirect:http://localhost:8087/login";
        }

        List<Topic> topics = new ArrayList<>(topicRepository.findAll());
        model.addAttribute("topics", topics);

        return "topics";
    }

    @RequestMapping(value = "/posts/edit={id}", method = RequestMethod.GET)
    public String editPost(@PathVariable("id") int id, Model model, String error, String message) {
        if(userServiceImplementation.getCurrentUser() == null) {
            model.addAttribute("message", "You need to log in.");
            return "redirect:http://localhost:8087/login";
        }

        if (error != null) {
            model.addAttribute("error", error);
        }
        if (message != null) {
            model.addAttribute("message", message);
        }

        Post post = postRepository.findById(id);
        User user = userRepository.findById(post.getIdAuthor());
        model.addAttribute("post", post);
        model.addAttribute("author", user);

        if (userServiceImplementation.getCurrentUserRole().getId() == 3L ||
                userServiceImplementation.isCurrentUser(user.getId())) {
            model.addAttribute("editPost", new Post());
        }

        return "editPost";
    }

    @RequestMapping(value = "/posts/edit={id}", method = RequestMethod.POST)
    public String editPost(@PathVariable("id") int id, @ModelAttribute("editPost") Post editPost, Model model) {
        if (editPost != null) {
            if (editPost.getMessage().length() < 1) {
                model.addAttribute("error", "Complete the empty field.");
                return "redirect:http://localhost:8087/posts/edit=" + id;
            }


            postServiceImplementation.updateMessage(postRepository.findById(id), editPost);
            model.addAttribute("message", "Post has been updated.");
        }
        return "redirect:http://localhost:8087/posts/edit=" + id;
    }
}
