package com.crowdfunding.repository;

import com.crowdfunding.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface PostRepository extends JpaRepository<Post, Long> {
}
