package com.crowdfunding.service;


import com.crowdfunding.model.Post;

public interface PostService {
    void createPost(Post post, String string);

    void updateMessage(Post post, Post editPost);
}
