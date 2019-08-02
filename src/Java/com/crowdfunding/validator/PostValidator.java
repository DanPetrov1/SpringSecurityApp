package com.crowdfunding.validator;

import com.crowdfunding.model.Post;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Post.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Post post = (Post) o;

        if (post.getMessage().length() < 1) {
            errors.rejectValue("message", "Empty.postForm");
        }
    }
}
