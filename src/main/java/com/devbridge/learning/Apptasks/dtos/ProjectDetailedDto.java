package com.devbridge.learning.Apptasks.dtos;

import com.devbridge.learning.Apptasks.models.Role;
import com.devbridge.learning.Apptasks.models.Status;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class ProjectDetailedDto {
    private UUID projectId;
    private String projectName;
    private UUID createdById;
    private String createdByFirstName;
    private String createdByLastName;

    private OffsetDateTime createdDate;
    private OffsetDateTime startDate;
    private OffsetDateTime initialDeadlineDate;
    private OffsetDateTime endDate;

    private Status status;
    private Integer progress;

    @Builder.Default
    private Set<EmployeeNameDto> participants = new HashSet<>();
    @Builder.Default
    private Set<TeamNameDto> teams = new HashSet<>();
}
