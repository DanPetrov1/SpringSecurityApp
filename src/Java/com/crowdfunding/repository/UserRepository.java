package com.crowdfunding.repository;

import com.crowdfunding.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);

    List<User> findAll();

    void deleteById(int id);

    User findById(int id);
}
