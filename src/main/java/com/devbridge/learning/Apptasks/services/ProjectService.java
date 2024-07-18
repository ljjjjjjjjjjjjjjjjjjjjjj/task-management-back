package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.models.Project;
import com.devbridge.learning.Apptasks.models.Status;
import com.devbridge.learning.Apptasks.models.Team;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.repositories.ProjectRepository;
import com.devbridge.learning.Apptasks.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final EmployeeRepository employeeRepository;

    private final static String PROJECT_NOT_FOUND = "Project with given id not found";
    private final static String NO_PROJECTS = "Employee does not have any projects";
    private final static String TEAM_NOT_FOUND = "Team with given id not found";
    private final static String EMPLOYEE_NOT_FOUND = "Employee with given id not found";

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByEmployeeId(UUID employeeId) {
        Employee employee = validateEmployee(employeeId);

        Set<UUID> teamIds = new HashSet<>();

        if (employee.getTeamId()!= null) {
            validateTeamId(employee.getTeamId());
            teamIds.add(employee.getTeamId());
        }

        teamIds.addAll(teamRepository.findTeamIdsByLeaderId(employeeId));

        Set<Project> projects = new HashSet<>();
        if (!teamIds.isEmpty()) {
            projects.addAll(projectRepository.findByTeamIds(teamIds));
        }
        projects.addAll(projectRepository.findByCreatedById(employeeId));

        return new ArrayList<>(projects);
    }

    public Project getProjectById(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND));
    }

    public Project createProject(Project project) {
        validateEmployee(project.getCreatedById());

        if (project.getTeamId() != null) {
            validateTeamId(project.getTeamId());
        }
        project.setProjectId(UUID.randomUUID());
        project.setCreatedDate(OffsetDateTime.now(ZoneOffset.UTC));
        if (project.getStatus() == null) {
            project.setStatus(Status.NOT_STARTED);
        }
        if (project.getProgress() == 0) {
            project.setProgress(0);
        }
        projectRepository.create(project);
        return project;
    }

    public Project updateProject(UUID projectId, Project project) {
        Project existingProject = validateProjectId(projectId);
        if (project.getTeamId() != null){
            validateTeamId(project.getTeamId());
        }

        existingProject.setProjectName(project.getProjectName());
        existingProject.setTeamId(project.getTeamId());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setInitialDeadlineDate(project.getInitialDeadlineDate());
        existingProject.setEndDate(project.getEndDate());
        existingProject.setStatus(project.getStatus());
        existingProject.setProgress(project.getProgress());

        projectRepository.update(existingProject);
        return existingProject;
    }

    public void deleteProject(UUID projectId) {
        projectRepository.delete(projectId);
    }

    private Employee validateEmployee (UUID employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));
    }

    private Team validateTeamId(UUID teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }

    private Project validateProjectId(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND));
    }

}