package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.ProjectDetailedDto;
import com.devbridge.learning.Apptasks.models.Project;
import com.devbridge.learning.Apptasks.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/employee/{employeeId}")
    public List<Project> getUserProjects(@PathVariable UUID employeeId) {
        return projectService.getProjectsByEmployeeId(employeeId);
    }

    @GetMapping("/detailed/employee/{employeeId}")
    public List<ProjectDetailedDto> getUserProjectsDetailed(@PathVariable UUID employeeId) {
        return projectService.getDetailedProjectsByEmployeeId(employeeId);
    }

    @GetMapping("/employee/{employeeId}/status/{status}")
    public List<Project> getUserProjectsByStatus(@PathVariable UUID employeeId, @PathVariable String status) {
        return projectService.getProjectsByEmployeeIdAndStatus(employeeId, status);
    }

    @GetMapping("/detailed/employee/{employeeId}/status/{status}")
    public List<ProjectDetailedDto> getUserProjectsDetailedByStatus(@PathVariable UUID employeeId, @PathVariable String status) {
        return projectService.getDetailedProjectsByEmployeeIdAndStatus(employeeId, status);
    }

    @GetMapping("/employee/createdBy/{employeeId}")
    public List<Project> getProjectsByCreatedById(@PathVariable UUID employeeId) {
        return projectService.getProjectsByCreatedById(employeeId);
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

    @PostMapping("/{projectId}/addParticipant/{employeeId}")
    public Project addParticipant(@PathVariable UUID projectId, @PathVariable UUID employeeId) {
        return projectService.addParticipant(projectId, employeeId);
    }

    @PostMapping("/{projectId}/removeParticipant/{employeeId}")
    public Project removeParticipant(@PathVariable UUID projectId, @PathVariable UUID employeeId) {
        return projectService.removeParticipant(projectId, employeeId);
    }

    @PostMapping("/{projectId}/addParticipantsByTeam/{teamId}")
    public Project addParticipantsByTeam(@PathVariable UUID projectId, @PathVariable UUID teamId) {
        return projectService.addParticipantsByTeam(projectId, teamId);
    }

    @PostMapping("/{projectId}/removeParticipantsByTeam/{teamId}")
    public Project removeParticipantsByTeam(@PathVariable UUID projectId, @PathVariable UUID teamId) {
        return projectService.removeParticipantsByTeam(projectId, teamId);
    }

    @PostMapping("/{projectId}/addTeam/{teamId}")
    public Project addTeam(@PathVariable UUID projectId, @PathVariable UUID teamId) {
        return projectService.addTeam(projectId, teamId);
    }

    @PostMapping("/{projectId}/removeTeam/{teamId}")
    public Project removeTeam(@PathVariable UUID projectId, @PathVariable UUID teamId) {
        return projectService.removeTeam(projectId, teamId);
    }

    @DeleteMapping("/{projectId}")
    public void deleteProject(@PathVariable UUID projectId) {
        projectService.deleteProject(projectId);
    }
}