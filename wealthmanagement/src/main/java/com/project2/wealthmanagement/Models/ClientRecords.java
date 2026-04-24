package com.project2.wealthmanagement.Models;

import java.util.ArrayList;
import java.util.List;

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
    private String clientRecordsId; // Apparently Cosmos likes Strings more than ints for IDs
    private String firstName;
    private String lastName;

    private ClientTier clientTier;
    private String country;
    private RiskTolerance riskTolerance;
    private PrimaryObjective primaryObjective;
    private List<String> goalIds = new ArrayList<>();


    public ClientRecords() {
    }


    public ClientRecords(String clientRecordsId, String firstName, String lastName, ClientTier clientTier,
            String country, RiskTolerance riskTolerance, PrimaryObjective primaryObjective, List<String> goalIds) {
        this.clientRecordsId = clientRecordsId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientTier = clientTier;
        this.country = country;
        this.riskTolerance = riskTolerance;
        this.primaryObjective = primaryObjective;
        this.goalIds = goalIds;
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


    public List<String> getGoalIds() {
        return goalIds;
    }


    public void setGoalIds(List<String> goalIds) {
        this.goalIds = goalIds;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientRecordsId == null) ? 0 : clientRecordsId.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((clientTier == null) ? 0 : clientTier.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((riskTolerance == null) ? 0 : riskTolerance.hashCode());
        result = prime * result + ((primaryObjective == null) ? 0 : primaryObjective.hashCode());
        result = prime * result + ((goalIds == null) ? 0 : goalIds.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientRecords other = (ClientRecords) obj;
        if (clientRecordsId == null) {
            if (other.clientRecordsId != null)
                return false;
        } else if (!clientRecordsId.equals(other.clientRecordsId))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (clientTier != other.clientTier)
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (riskTolerance != other.riskTolerance)
            return false;
        if (primaryObjective != other.primaryObjective)
            return false;
        if (goalIds == null) {
            if (other.goalIds != null)
                return false;
        } else if (!goalIds.equals(other.goalIds))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "ClientRecords [clientRecordsId=" + clientRecordsId + ", firstName=" + firstName + ", lastName="
                + lastName + ", clientTier=" + clientTier + ", country=" + country + ", riskTolerance=" + riskTolerance
                + ", primaryObjective=" + primaryObjective + ", goalIds=" + goalIds + "]";
    }

    

}
