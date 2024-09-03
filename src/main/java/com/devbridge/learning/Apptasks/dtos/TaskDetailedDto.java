package com.devbridge.learning.Apptasks.dtos;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDetailedDto {
    private UUID taskId;
    private String title;
    private UUID categoryId;
    private String description;

    private UUID createdById;
    private String createdByFirstName;
    private String createdByLastName;
    private UUID assignedToId;
    private String assignedToFirstName;
    private String assignedToLastName;

    private String status;
    private String priority;

    private UUID projectId;
    private String projectName;

    private OffsetDateTime createdDate;
    private OffsetDateTime assignedDate;
    private OffsetDateTime unassignedDate;
    private OffsetDateTime doneDate;
}
