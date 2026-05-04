package com.project2.wealthmanagement.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project2.wealthmanagement.Models.User;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByGoogleId(String googleId);  

    Optional<User> findByEmail(String email);

}