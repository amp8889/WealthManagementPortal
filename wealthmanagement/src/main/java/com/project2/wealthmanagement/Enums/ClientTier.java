package com.project2.wealthmanagement.Enums;

public enum ClientTier {

    STANDARD("Standard"),
    PREMIUM("Premium"),
    PRIVATE_BANKING("Private Banking");





    private final String displayName;

    ClientTier(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
