package com.project2.wealthmanagement.Models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

@Document(collation = "advisorInfo")
public class AdviorInfo {

    @Id
    private String advisorId;

    private String firstName;
    private String lastName;
    private String email;
    private String specialization;
    


}
