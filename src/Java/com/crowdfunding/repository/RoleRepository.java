package com.crowdfunding.repository;

import com.crowdfunding.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(long id);
}
