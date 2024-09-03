package com.devbridge.learning.Apptasks.dtos;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDetailedWithPhotosDto {
    private UUID taskId;
    private String title;
    private UUID categoryId;
    private String description;

    private EmployeeNameAndImageDto createdByEmployee;
    private EmployeeNameAndImageDto assignedToEmployee;

    private String status;
    private String priority;

    private UUID projectId;
    private String projectName;

    private OffsetDateTime createdDate;
    private OffsetDateTime assignedDate;
    private OffsetDateTime unassignedDate;
    private OffsetDateTime doneDate;
}
