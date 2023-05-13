package com.hhb.trainbookingsystem.enums;

public enum OperationType {

    UNKNOWN("unknown"),
    DELETE("delete"),
    UPDATE("update"),
    INSERT("insert"),
    SELETE("selete");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    OperationType(String s){
        this.value=s;
    }
}
