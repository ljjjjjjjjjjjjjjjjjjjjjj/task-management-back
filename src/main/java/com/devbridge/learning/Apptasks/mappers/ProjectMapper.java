package com.devbridge.learning.Apptasks.mappers;

import com.devbridge.learning.Apptasks.dtos.ProjectDetailedDto;
import com.devbridge.learning.Apptasks.models.Project;
import com.devbridge.learning.Apptasks.services.EmployeeService;
import com.devbridge.learning.Apptasks.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final EmployeeService employeeService;
    private final TeamService teamService;
    public ProjectDetailedDto toDto(Project project) {
        if (project == null) {
            return null;
        }

        return ProjectDetailedDto.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .createdById(project.getCreatedById())
                .createdDate(project.getCreatedDate())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .status(project.getStatus())
                .progress(project.getProgress())
                .participants(
                        employeeService.getEmployeeNameDtosByIds(
                                project.getParticipantIds()
                        )
                )
                .teams(
                        teamService.getTeamNameDtosByIds(
                        project.getTeamIds()
                        )
                )
                .build();
    }



}