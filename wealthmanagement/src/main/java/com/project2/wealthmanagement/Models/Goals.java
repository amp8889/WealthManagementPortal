package com.project2.wealthmanagement.Models;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;

import com.project2.wealthmanagement.Enums.GoalType;

import jakarta.persistence.Id;

@Document(collection = "goals")
public class Goals {

    @Id
    private String goalId;
    private String goalName;
    private int targetAmount;
    private GoalType goalType;
    private LocalDate goalDate;
    private int currentSavedAmount;



}
