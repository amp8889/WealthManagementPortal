package com.project2.wealthmanagement.Enums;

public enum UserRole {

    ADVISOR("Advisor"),
    AUDITOR("Auditor"),
    CLIENT("Client"),
    UNREGISTERED("Unregistered");

    private final String displayName;

    UserRole(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
