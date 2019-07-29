package com.crowdfunding.validator;

import com.crowdfunding.model.User;
import com.crowdfunding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (user.getUsername().length() < 1) {
            errors.rejectValue("username", "Empty.userForm");
            return;
        }

        if(userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
            return;
        }

        if (user.getEmail().length() < 1) {
            errors.rejectValue("email", "Empty.userForm");
            return;
        }

        if (user.getPassword().length() < 1) {
            errors.rejectValue("password", "Empty.userForm");
            return;
        }

        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
            return;
        }

        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
            return;
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
            return;
        }

        if (user.getEmail().length() < 8 || user.getEmail().length() > 32) {
            errors.rejectValue("email", "Size.userForm.email");
        }
    }
}
