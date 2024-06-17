package com.devbridge.learning.Apptasks.exceptions;

public class RegistrationError {
    public static String EMAIL_EXISTS = "This email is already registered.";
    public static String EMAIL_DOMAIN = "Invalid email domain. Only company employees can register.";
    public static String NAME_TOO_LONG = "First name must not exceed 64 characters.";
    public static String LAST_NAME_TOO_LONG = "Last name must not exceed 64 characters.";
    public static String EMAIL_TOO_LONG = "Email must not exceed 128 characters.";
    public static String JOB_TITLE_TOO_LONG = "Job title must not exceed 128 characters.";
    public static String CITY_NAME_TOO_LONG = "City must not exceed 128 characters.";
    public static String PASSWORD_REQUIREMENTS = "Password must be from 8 to 64 characters, include at least one letter, one number or symbol.";

}
