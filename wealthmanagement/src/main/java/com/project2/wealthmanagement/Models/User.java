package com.project2.wealthmanagement.Models;

import org.springframework.data.mongodb.core.mapping.Document;

import com.project2.wealthmanagement.Enums.UserRole;

import jakarta.persistence.Id;

// TODO: To be implemented further w/ Spring Security
@Document(collection = "user")
public class User {
    @Id
    private String userId;
    private UserRole role;

    // Only if role is advisor
    private String advisorId;
    private String firstName;
    private String lastName;
    private String email;

}