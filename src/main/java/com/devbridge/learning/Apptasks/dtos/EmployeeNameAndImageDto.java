package com.devbridge.learning.Apptasks.dtos;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeNameAndImageDto {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private UUID imageId;
    private byte[] imageData;

}
