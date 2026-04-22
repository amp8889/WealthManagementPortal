package com.project2.wealthmanagement.Enums;

public enum LogInteractionType {


    PORTFOLIO_REVIEW("Portfolio Review"),
    FINANCIAL_PLANNING_SESSION("Financial Planning Session"),
    TAX_PLANNING_CONSULTATION("Tax Planning Consultation"),
    ESTATE_PLANNING_DISCUSSION("Estate Planning Discussion");




    private final String displayName;

    LogInteractionType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
