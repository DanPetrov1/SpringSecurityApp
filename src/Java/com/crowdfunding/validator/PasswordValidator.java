package com.crowdfunding.validator;

import com.crowdfunding.model.Password;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Password.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Password password = (Password) o;

        if (password.getPassword().length() < 1) {
            errors.rejectValue("password", "Empty.userForm");
            return;
        }

        if (password.getPassword().length() < 8 || password.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
            return;
        }

        if (!password.getConfirmPassword().equals(password.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }
}
