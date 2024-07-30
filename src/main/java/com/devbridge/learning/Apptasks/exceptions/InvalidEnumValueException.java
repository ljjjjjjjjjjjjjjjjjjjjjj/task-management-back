package com.devbridge.learning.Apptasks.exceptions;

public class InvalidEnumValueException extends RuntimeException {
    public InvalidEnumValueException(String enumClassName, String value) {
        super("Invalid " + enumClassName + " value: " + value);
    }
}
