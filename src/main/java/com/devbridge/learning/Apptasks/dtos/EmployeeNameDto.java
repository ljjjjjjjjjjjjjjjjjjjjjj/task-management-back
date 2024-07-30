package com.devbridge.learning.Apptasks.dtos;

import com.devbridge.learning.Apptasks.models.Role;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class EmployeeNameDto {
    private UUID employeeId;
    private String firstName;
    private String lastName;
}
