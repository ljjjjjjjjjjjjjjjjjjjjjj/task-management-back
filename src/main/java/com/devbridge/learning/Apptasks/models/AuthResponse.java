package com.devbridge.learning.Apptasks.models;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import lombok.Data;

@Data
public class AuthResponse {
    private final String jwt;
    private final EmployeeDto user;
}