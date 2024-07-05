package com.devbridge.learning.Apptasks.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private UUID projectId;
    private String projectName;
    private UUID teamId;
    private OffsetDateTime createdDate;
    private OffsetDateTime startDate;
    private OffsetDateTime initialDeadlineDate;
    private OffsetDateTime endDate;
    private Status status;
    private int progress;
}