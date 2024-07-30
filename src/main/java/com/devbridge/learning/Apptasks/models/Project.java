package com.devbridge.learning.Apptasks.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private UUID projectId;
    private String projectName;
    private UUID createdById;
    private OffsetDateTime createdDate;
    private OffsetDateTime startDate;
    private OffsetDateTime initialDeadlineDate;
    private OffsetDateTime endDate;
    private Status status;
    private Integer progress;
    @Builder.Default
    private Set<UUID> participantIds = new HashSet<>();
    @Builder.Default
    private Set<UUID> teamIds = new HashSet<>();
}