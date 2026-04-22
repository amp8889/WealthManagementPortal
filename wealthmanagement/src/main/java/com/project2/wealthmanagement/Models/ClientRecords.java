package com.project2.wealthmanagement.Models;
import org.springframework.data.mongodb.core.mapping.Document;

import com.project2.wealthmanagement.Enums.ClientTier;
import com.project2.wealthmanagement.Enums.PrimaryObjective;
import com.project2.wealthmanagement.Enums.RiskTolerance;

import jakarta.persistence.Id;







@Document(collection = "clientrecords")
public class ClientRecords {

    @Id
    private String clientRecordsId; //Apparently Cosmos likes Strings more than ints for IDs
    private String firstName;
    private String lastName;
    private ClientTier clientTier;
    private String country;
    private RiskTolerance riskTolerance;
    private PrimaryObjective primaryObjective;
}
