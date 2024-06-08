package com.devbridge.learning.Apptasks.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AssignmentDto {
    private UUID assignmentId;
    private String title;
    private String category;
    private String description;
    private UUID employeeId;
}
