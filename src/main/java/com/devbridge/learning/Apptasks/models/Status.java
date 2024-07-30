package com.devbridge.learning.Apptasks.models;

import com.devbridge.learning.Apptasks.exceptions.InvalidEnumValueException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Status {
    NOT_STARTED,
    IN_PROGRESS,
    IN_REVIEW,
    DONE;

    @JsonCreator
    public static Status fromString(String value) {
        return Arrays.stream(Status.values())
                .filter(status -> status.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new InvalidEnumValueException("status", value));
    }


    @JsonValue
    public String toValue() {
        return this.name();
    }
}
