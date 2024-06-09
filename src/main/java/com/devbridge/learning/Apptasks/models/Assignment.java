package com.devbridge.learning.Apptasks.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
    private UUID assignmentId;
    private String title;
    private Category category;
    private String description;
    private UUID createdById;
    private UUID assignedToId;
    private Status status;
    private Priority priority;
    private OffsetDateTime createdDate;
    private OffsetDateTime assignedDate;
    private OffsetDateTime unassignedDate;
    private OffsetDateTime doneDate;

}


