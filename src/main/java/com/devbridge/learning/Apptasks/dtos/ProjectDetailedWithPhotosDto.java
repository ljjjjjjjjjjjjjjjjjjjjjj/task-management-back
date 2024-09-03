package com.devbridge.learning.Apptasks.dtos;

import com.devbridge.learning.Apptasks.models.Status;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailedWithPhotosDto {
    private UUID projectId;
    private String projectName;
    private EmployeeNameAndImageDto createdByEmployee;

    private OffsetDateTime createdDate;
    private OffsetDateTime startDate;
    private OffsetDateTime initialDeadlineDate;
    private OffsetDateTime endDate;

    private Status status;
    private Integer progress;

    @Builder.Default
    private Set<EmployeeNameAndImageDto> participants = new HashSet<>();
    @Builder.Default
    private Set<TeamNameDto> teams = new HashSet<>();
}

