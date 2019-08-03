package com.crowdfunding.repository;

import com.crowdfunding.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findByTopicName(String topicName);

    Topic findById(int id);
}
