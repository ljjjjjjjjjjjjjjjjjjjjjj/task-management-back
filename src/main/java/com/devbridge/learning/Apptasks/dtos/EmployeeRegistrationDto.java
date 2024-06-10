package com.devbridge.learning.Apptasks.dtos;

import com.devbridge.learning.Apptasks.models.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
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
