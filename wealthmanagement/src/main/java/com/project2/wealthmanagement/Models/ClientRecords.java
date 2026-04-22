package com.project2.wealthmanagement.Models;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;
import com.project2.wealthmanagement.Enums.ClientTier;
import com.project2.wealthmanagement.Enums.PrimaryObjective;
import com.project2.wealthmanagement.Enums.RiskTolerance;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;







@Document(collection = "clientrecords")
public class ClientRecords {

    @Id
    @NonNull
    private String clientRecordsId; //Apparently Cosmos likes Strings more than ints for IDs
    private String firstName;
    private String lastName;
    private ClientTier clientTier;
    private String country;
    private RiskTolerance riskTolerance;
    private PrimaryObjective primaryObjective;



    public ClientRecords() {
    }



    public ClientRecords(String clientRecordsId, String firstName, String lastName, ClientTier clientTier,
            String country, RiskTolerance riskTolerance, PrimaryObjective primaryObjective) {
        this.clientRecordsId = clientRecordsId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientTier = clientTier;
        this.country = country;
        this.riskTolerance = riskTolerance;
        this.primaryObjective = primaryObjective;
    }



    public String getClientRecordsId() {
        return clientRecordsId;
    }



    public void setClientRecordsId(String clientRecordsId) {
        this.clientRecordsId = clientRecordsId;
    }



    public String getFirstName() {
        return firstName;
    }



    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



    public String getLastName() {
        return lastName;
    }



    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public ClientTier getClientTier() {
        return clientTier;
    }



    public void setClientTier(ClientTier clientTier) {
        this.clientTier = clientTier;
    }



    public String getCountry() {
        return country;
    }



    public void setCountry(String country) {
        this.country = country;
    }



    public RiskTolerance getRiskTolerance() {
        return riskTolerance;
    }



    public void setRiskTolerance(RiskTolerance riskTolerance) {
        this.riskTolerance = riskTolerance;
    }



    public PrimaryObjective getPrimaryObjective() {
        return primaryObjective;
    }



    public void setPrimaryObjective(PrimaryObjective primaryObjective) {
        this.primaryObjective = primaryObjective;
    }











    
}


