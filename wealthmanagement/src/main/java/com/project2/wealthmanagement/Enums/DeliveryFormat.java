package com.project2.wealthmanagement.Enums;

public enum DeliveryFormat {

    IN_PERSON("In Person"),
    VIRTUAL("Virtual");


    private final String displayName;

    DeliveryFormat(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
