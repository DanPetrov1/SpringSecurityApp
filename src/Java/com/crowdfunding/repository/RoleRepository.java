package com.crowdfunding.repository;

import com.crowdfunding.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(int id);
}
