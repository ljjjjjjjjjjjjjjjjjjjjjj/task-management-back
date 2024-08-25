package com.devbridge.learning.Apptasks.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class AuthenticationException extends RuntimeException {
    private final Map<String, Object> validationErrors;

    // validation errors, plus a custom error message
    public AuthenticationException(String message, Map<String, Object> errors) {
        super(message);
        this.validationErrors = errors;
    }

    // validation errors, no message
    public AuthenticationException(Map<String, Object> errors) {
        super("Authentication failed");  // default
        this.validationErrors = errors;
    }
}
