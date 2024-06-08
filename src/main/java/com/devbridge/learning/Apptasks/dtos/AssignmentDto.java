package com.devbridge.learning.Apptasks.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class AssignmentDto {
    private UUID assignmentId;
    private String title;
    private String category;
    private String description;
    private UUID employeeId;
}
