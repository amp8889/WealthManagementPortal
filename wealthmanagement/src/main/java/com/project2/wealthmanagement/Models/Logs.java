package com.project2.wealthmanagement.Models;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.project2.wealthmanagement.Enums.DeliveryFormat;
import com.project2.wealthmanagement.Enums.LogInteractionType;

import jakarta.persistence.Id;

@Document(collation = "logs")
public class Logs {



    @Id
    private String logId;

    private LogInteractionType logInteractionType;
    private LocalDate logDate;
    private DeliveryFormat deliveryFormat;
    private String summary;
}
