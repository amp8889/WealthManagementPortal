package com.project2.wealthmanagement.Enums;

public enum RiskTolerance {

    CONSERVATIVE("Conservative"),
    MODERATE("Moderate"),
    AGGRESSIVE("Aggressive");




    private final String displayName;

    RiskTolerance(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
