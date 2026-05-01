package com.project2.wealthmanagement.Models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.project2.wealthmanagement.Enums.GoalType;


@Document(collection = "goal")
public class Goal {

    @Id
    private String goalId;
    private String goalName;
    private int targetAmount;
    private GoalType goalType;
    private LocalDate goalDate;
    private int currentSavedAmount;
    private String clientId;

    public Goal() {
    }

    public Goal(String goalId, String goalName, int targetAmount, GoalType goalType, LocalDate goalDate,
            int currentSavedAmount, String clientId) {
        this.goalId = goalId;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.goalType = goalType;
        this.goalDate = goalDate;
        this.currentSavedAmount = currentSavedAmount;
        this.clientId = clientId;
    }

    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    public LocalDate getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(LocalDate goalDate) {
        this.goalDate = goalDate;
    }

    public int getCurrentSavedAmount() {
        return currentSavedAmount;
    }

    public void setCurrentSavedAmount(int currentSavedAmount) {
        this.currentSavedAmount = currentSavedAmount;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((goalId == null) ? 0 : goalId.hashCode());
        result = prime * result + ((goalName == null) ? 0 : goalName.hashCode());
        result = prime * result + targetAmount;
        result = prime * result + ((goalType == null) ? 0 : goalType.hashCode());
        result = prime * result + ((goalDate == null) ? 0 : goalDate.hashCode());
        result = prime * result + currentSavedAmount;
        result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
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
        Goal other = (Goal) obj;
        if (goalId == null) {
            if (other.goalId != null)
                return false;
        } else if (!goalId.equals(other.goalId))
            return false;
        if (goalName == null) {
            if (other.goalName != null)
                return false;
        } else if (!goalName.equals(other.goalName))
            return false;
        if (targetAmount != other.targetAmount)
            return false;
        if (goalType != other.goalType)
            return false;
        if (goalDate == null) {
            if (other.goalDate != null)
                return false;
        } else if (!goalDate.equals(other.goalDate))
            return false;
        if (currentSavedAmount != other.currentSavedAmount)
            return false;
        if (clientId == null) {
            if (other.clientId != null)
                return false;
        } else if (!clientId.equals(other.clientId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Goal [goalId=" + goalId + ", goalName=" + goalName + ", targetAmount=" + targetAmount + ", goalType="
                + goalType + ", goalDate=" + goalDate + ", currentSavedAmount=" + currentSavedAmount + ", clientId="
                + clientId + "]";
    }




}
