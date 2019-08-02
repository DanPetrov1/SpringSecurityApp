package com.crowdfunding.service;

import com.crowdfunding.model.Topic;
import com.crowdfunding.model.User;
import com.crowdfunding.repository.TopicRepository;
import com.crowdfunding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImplementation implements TopicService {

    @Autowired
    SecurityServiceImplementation securityServiceImplementation;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void createTopic(String topicName) {
        String currentUsername = securityServiceImplementation.findLoggedInUsername().getUsername();
        User user = userRepository.findByUsername(currentUsername);
        if (user.getUsername() == null) {return;}

        Topic topic = new Topic();
        topic.setTopicName(topicName);
        topic.setIdAuthor(user.getId());
        topicRepository.save(topic);
    }
}
