package com.lhind.util;

public enum BadRequest {

    USER_EXISTS("User already exists!"),
    PROBATION_PERIOD_NOT_PASSED("You have not passed the probation period yet!"),
    APPLICATION_ALREADY_REQUESTED("Application already requested during this time or you have inserted wrong date!"),
    NOT_SUFFICIENT_DAYS("You have required more days than your remaining days!"),
    APPLICATION_REJECTED("You can't modify REJECTED Applications!"),
    PASSWORD_SAME_AS_OLD("New password can't be same as old password"),
    OLD_PASS_NOT_MATCH("Old password does not match"),
    WRONG_FORMAT("Wrong date format");


    private String message;

    public String getMessage() {
        return message;
    }

    private BadRequest(String message) {
        this.message = message;
    }
}
