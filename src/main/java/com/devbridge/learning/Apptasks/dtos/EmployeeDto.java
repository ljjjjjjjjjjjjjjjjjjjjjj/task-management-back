package com.devbridge.learning.Apptasks.dtos;

import com.devbridge.learning.Apptasks.models.Role;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private UUID teamId;
    private UUID imageId;
    private Set<Role> roles;
}
