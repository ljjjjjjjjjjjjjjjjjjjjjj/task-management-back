package com.devbridge.learning.Apptasks.dtos;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDto {
    private UUID teamId;
    private String teamName;
    private UUID teamLeaderId;
    @Builder.Default
    private Set<EmployeeDto> teamMembers = new HashSet<>();
}
