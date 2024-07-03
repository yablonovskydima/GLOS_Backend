package com.accesstools;

public enum AccessNodeType {

    NONE, USER, GROUP, OWNER;

    public static AccessNodeType fromName(String name) {
        String name0 = name.trim().toUpperCase();
        return valueOf(name0);
    }

}
