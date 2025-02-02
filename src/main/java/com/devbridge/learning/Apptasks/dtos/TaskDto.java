package com.devbridge.learning.Apptasks.dtos;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private UUID taskId;
    private String title;
    private UUID categoryId;
    private String description;
    private UUID createdById;
    private UUID assignedToId;
    private String status;
    private String priority;
    private UUID projectId;
    private OffsetDateTime createdDate;
    private OffsetDateTime assignedDate;
    private OffsetDateTime unassignedDate;
    private OffsetDateTime doneDate;
}