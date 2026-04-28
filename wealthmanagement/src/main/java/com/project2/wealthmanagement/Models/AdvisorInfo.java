package com.project2.wealthmanagement.Models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

@Document(collection = "advisorinfo")
public class AdvisorInfo {

    @Id
    private String advisorId;
    private String userId;
    private String specialization;
    List<String> assignedClientIds;

    public AdvisorInfo() {
    }

    public AdvisorInfo(String advisorId, String userId, String specialization, List<String> assignedClientIds) {
        this.advisorId = advisorId;
        this.userId = userId;
        this.specialization = specialization;
        this.assignedClientIds = assignedClientIds;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<String> getAssignedClientIds() {
        return assignedClientIds;
    }

    public void setAssignedClientIds(List<String> assignedClientIds) {
        this.assignedClientIds = assignedClientIds;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((advisorId == null) ? 0 : advisorId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((specialization == null) ? 0 : specialization.hashCode());
        result = prime * result + ((assignedClientIds == null) ? 0 : assignedClientIds.hashCode());
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
        AdvisorInfo other = (AdvisorInfo) obj;
        if (advisorId == null) {
            if (other.advisorId != null)
                return false;
        } else if (!advisorId.equals(other.advisorId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (specialization == null) {
            if (other.specialization != null)
                return false;
        } else if (!specialization.equals(other.specialization))
            return false;
        if (assignedClientIds == null) {
            if (other.assignedClientIds != null)
                return false;
        } else if (!assignedClientIds.equals(other.assignedClientIds))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AdvisorInfo [advisorId=" + advisorId + ", userId=" + userId + ", specialization=" + specialization
                + ", assignedClientIds=" + assignedClientIds + "]";
    }

}