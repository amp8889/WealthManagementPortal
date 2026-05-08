package com.project2.wealthmanagement.Configurations;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.project2.wealthmanagement.Models.User;
import com.project2.wealthmanagement.Repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())   
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
