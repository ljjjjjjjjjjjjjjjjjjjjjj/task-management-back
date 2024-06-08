package com.devbridge.learning.Apptasks.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class EmployeeRegistrationDto {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
