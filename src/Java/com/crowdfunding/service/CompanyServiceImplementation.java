package com.crowdfunding.service;

import com.crowdfunding.model.Company;
import com.crowdfunding.model.User;
import com.crowdfunding.repository.CompanyRepository;
import com.crowdfunding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        company.setCash((long)cash + company.getCash());
        user.setCash(user.getCash() - (long)cash);
        companyRepository.save(company);
        userRepository.save(user);
    }

    @Override
    public void addCompany(Company company) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        company.setDateFoundation(dateFormat.format(date));
        company.setCash(0L);
        company.setIdFounder(userServiceImplementation.getCurrentUser().getId());
        companyRepository.save(company);
    }
}
