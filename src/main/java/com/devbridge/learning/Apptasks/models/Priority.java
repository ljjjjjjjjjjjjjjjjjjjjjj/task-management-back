package com.devbridge.learning.Apptasks.models;

import com.devbridge.learning.Apptasks.exceptions.InvalidEnumValueException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Priority {
    HIGHEST,
    HIGH,
    MEDIUM,
    LOW,
    LOWEST;

    @JsonCreator
    public static Priority  fromString(String value) {
        return Arrays.stream(Priority.values())
                .filter(priority -> priority.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new InvalidEnumValueException("priority", value));
    }


    @JsonValue
    public String toValue() {
        return this.name();
    }
}
