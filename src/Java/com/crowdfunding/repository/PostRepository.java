package com.crowdfunding.repository;

import com.crowdfunding.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByIdTopic(int id);

    Post findById(int id);

    void deleteById(int id);
}
