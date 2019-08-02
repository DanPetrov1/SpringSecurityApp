package com.crowdfunding.service;

import com.crowdfunding.model.Post;
import com.crowdfunding.model.Topic;
import com.crowdfunding.model.User;
import com.crowdfunding.repository.PostRepository;
import com.crowdfunding.repository.TopicRepository;
import com.crowdfunding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TopicServiceImplementation topicServiceImplementation;

    @Autowired
    SecurityServiceImplementation securityServiceImplementation;

    @Override
    public void createPost(Post post, String topicName) {
        String currentUsername = securityServiceImplementation.findLoggedInUsername().getUsername();
        User user = userRepository.findByUsername(currentUsername);
        if (user.getUsername() == null) {return;}

        Topic topic = topicRepository.findByTopicName(topicName);
        if (topic == null) {
            topicServiceImplementation.createTopic(topicName);
            topic = topicRepository.findByTopicName(topicName);
        }

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(date);

        post.setIdTopic(topic.getId());
        post.setIdAuthor(user.getId());
        post.setCreationDate(currentTime);

        postRepository.save(post);
    }
}
