package com.crowdfunding.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImplementation implements SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImplementation.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public User findLoggedInUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (User) auth.getPrincipal();
        }

        return null;
    }
}
