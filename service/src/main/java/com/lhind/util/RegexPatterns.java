package com.lhind.util;

public class RegexPatterns {
    public static final String FORMAT = "^[a-zA-Z0-9]{3,15}";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\wds:])([^\\s]){6,16}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_PATTERN = "dd.MM.yyyy";
}
