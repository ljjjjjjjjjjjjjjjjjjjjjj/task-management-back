package com.devbridge.learning.Apptasks.dtos;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeNameDto {
    private UUID employeeId;
    private String firstName;
    private String lastName;
}
