package com.crowdfunding.service;

import com.crowdfunding.model.Company;
import com.crowdfunding.model.User;
import com.crowdfunding.repository.CompanyRepository;
import com.crowdfunding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImplementation implements CompanyService {

    @Autowired
    UserServiceImplementation userServiceImplementation;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public void share(Company company, int cash) {
        User user = userServiceImplementation.getCurrentUser();
        company.setCash((long)cash);
        user.setCash(user.getCash() - (long)cash);
        companyRepository.save(company);
        userRepository.save(user);
    }
}
