package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.ProjectDetailedDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.mappers.EmployeeMapper;
import com.devbridge.learning.Apptasks.mappers.ProjectMapper;
import com.devbridge.learning.Apptasks.models.*;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.repositories.ProjectRepository;
import com.devbridge.learning.Apptasks.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectMapper projectMapper;

    private final static String PROJECT_NOT_FOUND = "Project with given id not found";
    private final static String TEAM_NOT_FOUND = "Team with given id not found";
    private final static String EMPLOYEE_NOT_FOUND = "Employee with given id not found";
    private final static String INVALID_PROGRESS = "Progress must be between 0 and 100.";

    private final static String EMPLOYEE_NOT_IN_PROJECT = "Employee is not in the project";
    private final static String EMPLOYEE_ALREADY_IN_PROJECT = "Employee already is in the project";

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByEmployeeId(UUID employeeId) {
        validateEmployee(employeeId);
        return projectRepository.findByEmployeeId(employeeId);
    }

    public List<ProjectDetailedDto> getDetailedProjectsByEmployeeId(UUID employeeId) {
        validateEmployee(employeeId);
        List <Project> userProjects = projectRepository.findByEmployeeId(employeeId);
        return userProjects.stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<Project> getProjectsByEmployeeIdAndStatus(UUID employeeId, String status) {
        validateEmployee(employeeId);
        return projectRepository.findByEmployeeIdAndStatus(employeeId,status);
    }

    public List<ProjectDetailedDto> getDetailedProjectsByEmployeeIdAndStatus(UUID employeeId, String status) {
        validateEmployee(employeeId);
        List <Project> userProjects = projectRepository.findByEmployeeIdAndStatus(employeeId,status);
        return userProjects.stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<Project> getProjectsByCreatedById(UUID employeeId) {
        validateEmployee(employeeId);
        return projectRepository.findByCreatedById(employeeId);
    }

    public Project getProjectById(UUID projectId) {
        return validateProjectId(projectId);
    }

    public Project createProject(Project project) {
        validateEmployee(project.getCreatedById());
        validateTeamIds(project.getTeamIds());
        validateEmployeesIds(project.getParticipantIds());
        validateAndSetProgress(project);

        UUID newProjectId = UUID.randomUUID();
        project.setProjectId(newProjectId);

        if (project.getStatus() == null) {
            project.setStatus(Status.NOT_STARTED);
        }

        setProjectDatesOnCreate(project);

        projectRepository.create(project);

        for (UUID teamId : project.getTeamIds()) {
            projectRepository.insertProjectTeam(project.getProjectId(), teamId);
        }

        for (UUID participantId : project.getParticipantIds()) {
            projectRepository.insertProjectParticipant(project.getProjectId(), participantId);
        }
        projectRepository.insertProjectParticipant(project.getProjectId(), project.getCreatedById());

        return validateProjectId(newProjectId);
    }

    public Project updateProject(UUID projectId, Project project) {
        Project existingProject = validateProjectId(projectId);
        validateTeamIds(project.getTeamIds());
        validateEmployeesIds(project.getParticipantIds());
        validateAndSetProgress(project);

        existingProject.setProjectName(project.getProjectName());
        existingProject.setInitialDeadlineDate(project.getInitialDeadlineDate());
        existingProject.setStatus(project.getStatus());
        existingProject.setProgress(project.getProgress());

        setProjectDatesOnUpdate(existingProject, project);

        projectRepository.update(existingProject);

        Set<UUID> existingTeamIds = teamRepository.findTeamIdsByProjectId(projectId);
        Set<UUID> newTeamIds = project.getTeamIds();

        for (UUID teamId : newTeamIds) {
            if (!existingTeamIds.contains(teamId)) {
                projectRepository.insertProjectTeam(projectId, teamId);
            }
        }

        for (UUID teamId : existingTeamIds) {
            if (!newTeamIds.contains(teamId)) {
                projectRepository.removeProjectTeam(projectId, teamId);
            }
        }

        Set<UUID> existingParticipantIds = employeeRepository.findEmployeeIdsByProjectId(projectId);
        Set<UUID> newParticipantIds = project.getParticipantIds();

        for (UUID participantId : newParticipantIds) {
            if (!existingParticipantIds.contains(participantId)) {
                projectRepository.insertProjectParticipant(projectId, participantId);
            }
        }

        for (UUID participantId : existingParticipantIds) {
            if (!newParticipantIds.contains(participantId)) {
                projectRepository.deleteProjectParticipant(projectId, participantId);
            }
        }

        return getProjectById(projectId);
    }

    public Project addParticipant(UUID projectId, UUID employeeId) {
        validateProjectId(projectId);
        validateEmployee(employeeId);

        if (projectRepository.existsProjectParticipant(projectId, employeeId)) {
            throw new EntityNotFoundException(EMPLOYEE_ALREADY_IN_PROJECT);
        }

        projectRepository.insertProjectParticipant(projectId, employeeId);
        return validateProjectId(projectId);
    }

    public Project removeParticipant(UUID projectId, UUID employeeId) {
        validateProjectId(projectId);
        validateEmployee(employeeId);

        if (!projectRepository.existsProjectParticipant(projectId, employeeId)) {
            throw new EntityNotFoundException(EMPLOYEE_NOT_IN_PROJECT);
        }

        projectRepository.deleteProjectParticipant(projectId, employeeId);
        return validateProjectId(projectId);
    }

    public Project addParticipantsByTeam(UUID projectId, UUID teamId) {
        validateProjectId(projectId);
        Team team = validateTeamId(teamId);

        for (Employee member : team.getTeamMembers()) {
            validateEmployee(member.getEmployeeId());
            projectRepository.insertProjectParticipant(projectId, member.getEmployeeId());
        }

        return validateProjectId(projectId);
    }

    public Project removeParticipantsByTeam(UUID projectId, UUID teamId) {
        validateProjectId(projectId);
        Team team = validateTeamId(teamId);

        for (Employee member : team.getTeamMembers()) {
            projectRepository.deleteProjectParticipant(projectId, member.getEmployeeId());
        }

        return validateProjectId(projectId);
    }

    public Project addTeam(UUID projectId, UUID teamId) {
        validateProjectId(projectId);
        validateTeamId(teamId);

        projectRepository.insertProjectTeam(projectId, teamId);
        return validateProjectId(projectId);
    }

    public Project removeTeam(UUID projectId, UUID teamId) {
        validateProjectId(projectId);
        validateTeamId(teamId);

        projectRepository.removeProjectTeam(projectId, teamId);
        return validateProjectId(projectId);
    }

    public void deleteProject(UUID projectId) {
        validateProjectId(projectId);
        projectRepository.delete(projectId);
    }

    private void setProjectDatesOnCreate(Project project) {
        project.setCreatedDate(OffsetDateTime.now(ZoneOffset.UTC));
        if (project.getStatus() != Status.NOT_STARTED) {
            project.setStartDate(OffsetDateTime.now(ZoneOffset.UTC));
        } else {
            project.setStartDate(null);
        }

        if (project.getStatus() == Status.DONE) {
            project.setEndDate(OffsetDateTime.now(ZoneOffset.UTC));
        } else {
            project.setEndDate(null);
        }
    }

    private void setProjectDatesOnUpdate(Project existingProject, Project project) {
        if (existingProject.getStatus() == Status.NOT_STARTED && project.getStatus() != Status.NOT_STARTED) {
            existingProject.setStartDate(OffsetDateTime.now(ZoneOffset.UTC));
        }

        if (existingProject.getStatus() != Status.DONE && project.getStatus() == Status.DONE) {
            existingProject.setEndDate(OffsetDateTime.now(ZoneOffset.UTC));
        }
    }

    private void validateEmployee (UUID employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));
    }

    private void validateEmployeesIds(Set<UUID> participantIds) {
        for (UUID participantId : participantIds) {
            validateEmployee(participantId);
        }
    }

    private Team validateTeamId(UUID teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }
    private void validateTeamIds(Set<UUID> teamIds) {
        for (UUID teamId : teamIds) {
            validateTeamId(teamId);
        }
    }

    private Project validateProjectId(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND));
    }

    private void validateAndSetProgress(Project project) {
        Integer progress = project.getProgress();

        if (progress == null) {
            project.setProgress(0);
        } else if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException(INVALID_PROGRESS);
        }
    }

}