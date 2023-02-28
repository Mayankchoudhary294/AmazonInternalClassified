package com.Amazon.models;

public enum Condition {
    BRAND_NEW("Brand new (Seal Packed)"),
    LIGHTLY_USED("Lightly Used"),
    MODERATELY_USED("Moderately Used"),
    HEAVILY_USED("Heavily Used"),
    DAMAGED("Damaged"),
    DENTED("Dented"),
    NOT_WORKING("Not Working");
    
    private final String displayName;
    
    Condition(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}

