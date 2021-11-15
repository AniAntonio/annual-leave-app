package com.lhind.util;

public enum NoData {
    USER_NOT_FOUND("User not found"),
    SUPERVISOR_NOT_FOUND("Supervisor not found"),
    ANNUAL_LEAVE_NOT_FOUND("Annual leave not found!"),
    EXCEL_ERROR("Error generating excel"),
    APPLICATION_NOT_FOUND("Application not found!");
    private String message;

    public String getMessage() {
        return message;
    }

    NoData(String message) {
        this.message = message;
    }
}
