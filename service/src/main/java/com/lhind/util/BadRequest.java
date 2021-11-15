package com.lhind.util;

public enum BadRequest {

   USER_EXISTS("User already exists!"),
    PROBATION_PERIOD_NOT_PASSED("You have not passed the probation period yet!"),
    NOT_SUFFICIENT_DAYS("You have required more days than your remaining days!"),
    NOT_ALLOWED("You are not allowed to perform this action"),
   APPLICATION_REJECTED("You can't modify REJECTED Applications!"),
   DUPLICATED_CLASSES ("Classes are duplicated!"),
   WRONG_CAPACITIES ("Class capacities are greater than total capacity!"),
    PASSWORD_SAME_AS_OLD("New password can't be same as old password"),
    OLD_PASS_NOT_MATCH("Old password does not match"),
    MAX_FLIGHTS_EXCEEDED("You are not allowed to book more than 20 flights per year"),
    WRONG_FORMAT("Wrong date format");



    private String message;

    public String getMessage() {
        return message;
    }

    private BadRequest(String message) {
        this.message = message;
    }
}
