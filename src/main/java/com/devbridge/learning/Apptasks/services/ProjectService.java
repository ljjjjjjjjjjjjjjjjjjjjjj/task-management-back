package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.models.Project;
import com.devbridge.learning.Apptasks.models.Status;
import com.devbridge.learning.Apptasks.repositories.ProjectRepository;
import com.devbridge.learning.Apptasks.repositories.TaskRepository;
import com.devbridge.learning.Apptasks.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final TaskRepository taskRepository;

    private final static String PROJECT_NOT_FOUND = "Project with given id not found";
    private final static String TEAM_NOT_FOUND = "Team with given id not found";
    private final static String TASK_NOT_FOUND = "Task with given id not found";
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND));
    }

    public Project createProject(Project project) {
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
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND));

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

    private void validateTeamId(UUID teamId) {
        if (teamRepository.findById(teamId).isEmpty()) {
            throw new EntityNotFoundException(TEAM_NOT_FOUND);
        }
    }
}