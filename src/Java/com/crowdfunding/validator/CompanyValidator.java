package com.crowdfunding.validator;

import com.crowdfunding.model.Company;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CompanyValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Company.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Company company = (Company) o;

        if (company.getName().length() < 1) {
            errors.rejectValue("name", "Empty.postForm");
            return;
        }

        if (company.getDescription().length() < 1) {
            errors.rejectValue("description", "Empty.postForm");
        }
    }
}
