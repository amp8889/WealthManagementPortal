package com.project2.wealthmanagement.Enums;

public enum PrimaryObjective {

    GROWTH("Growth"),
    INCOME("Income"),
    CAPITAL_PRESERVATION("Capital Preservation"),
    BALANCED("Balanced")



    private final String displayName;

    PrimaryObjective(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
