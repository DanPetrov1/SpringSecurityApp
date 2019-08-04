package com.crowdfunding.repository;

import com.crowdfunding.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findById(int id);
}
