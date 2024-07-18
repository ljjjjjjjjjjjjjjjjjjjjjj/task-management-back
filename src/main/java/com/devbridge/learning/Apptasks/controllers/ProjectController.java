package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.TaskDto;
import com.devbridge.learning.Apptasks.models.Project;
import com.devbridge.learning.Apptasks.services.ProjectService;
import com.devbridge.learning.Apptasks.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/employee/{employeeId}")
    public List<Project> getProjectsByEmployeeId(@PathVariable UUID employeeId) {
        return projectService.getProjectsByEmployeeId(employeeId);
    }

    @GetMapping("/{projectId}")
    public Project getProjectById(@PathVariable UUID projectId) {
        return projectService.getProjectById(projectId);
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/{projectId}")
    public Project updateProject(@PathVariable UUID projectId, @RequestBody Project project) {
        return projectService.updateProject(projectId, project);
    }

    @DeleteMapping("/{projectId}")
    public void deleteProject(@PathVariable UUID projectId) {
        projectService.deleteProject(projectId);
    }
}