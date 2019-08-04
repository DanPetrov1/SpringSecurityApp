package com.crowdfunding.service;

import com.crowdfunding.model.Company;

public interface CompanyService {
    void share(Company byId, int cash);
}
