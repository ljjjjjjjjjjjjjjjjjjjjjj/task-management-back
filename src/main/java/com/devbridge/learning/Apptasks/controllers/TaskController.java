package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.TaskDetailedDto;
import com.devbridge.learning.Apptasks.dtos.TaskDetailedWithPhotosDto;
import com.devbridge.learning.Apptasks.dtos.TaskDto;
import com.devbridge.learning.Apptasks.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskId}")
    public TaskDto getTaskById(@PathVariable UUID taskId) {
        return taskService.getTaskById(taskId);
    }

    @GetMapping("/detailed/{taskId}")
    public TaskDetailedWithPhotosDto getTaskDetailedWithPhotosById(@PathVariable UUID taskId) {
        return taskService.getTaskDetailedById(taskId);
    }

    @GetMapping("/project/{projectId}")
    public List<TaskDto> getTasksByProjectId(@PathVariable UUID projectId) {
        return taskService.getTasksByProjectId(projectId);
    }

    @GetMapping("/detailed/project/{projectId}")
    public List<TaskDetailedWithPhotosDto> getTasksDetailedWithPhotosByProjectId(@PathVariable UUID projectId) {
        return taskService.getTasksDetailedByProjectId(projectId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<TaskDto> getTasksByEmployeeId(@PathVariable UUID employeeId) {
        return taskService.getTasksByEmployeeId(employeeId);
    }

    @GetMapping("/detailed/employee/{employeeId}")
    public List<TaskDetailedWithPhotosDto> getTasksDetailedWithPhotosByEmployeeId(@PathVariable UUID employeeId) {
        return taskService.getTasksDetailedByEmployeeId(employeeId);
    }

    @GetMapping("/detailed/assignedto/{employeeId}")
    public List<TaskDetailedWithPhotosDto> getTasksDetailedWithPhotosByAssignedToId(@PathVariable UUID employeeId) {
        return taskService.getTasksDetailedByEmployeeId(employeeId);
    }

    @GetMapping("/detailed/createdby/{employeeId}")
    public List<TaskDetailedWithPhotosDto> getTasksDetailedWithPhotosByCreatedById(@PathVariable UUID employeeId) {
        return taskService.getTasksDetailedByEmployeeId(employeeId);
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping("/{taskId}")
    public TaskDto updateTask(@PathVariable UUID taskId, @RequestBody TaskDto taskDto) {
        return taskService.updateTask(taskId, taskDto);
    }

    @PutMapping("/{taskId}/project")
    public TaskDto assignTaskToProject(@PathVariable UUID taskId, @RequestParam UUID projectId) {
        return taskService.assignTaskToProject(taskId, projectId);
    }


    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
    }
}