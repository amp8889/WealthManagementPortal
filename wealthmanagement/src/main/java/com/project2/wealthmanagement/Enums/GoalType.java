package com.project2.wealthmanagement.Enums;

public enum GoalType {


    RETIREMENT("Retirement"),
    EDUCATION("Education"),
    HOME_PURCHASE("Home Purchase"),
    WEALTH_ACCUMULATION("Wealth Accumulation"),
    EMERGENCY_FUND("Emergency Fund");

    private final String displayName;

    GoalType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
