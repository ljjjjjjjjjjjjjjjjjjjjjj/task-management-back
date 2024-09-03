package com.devbridge.learning.Apptasks.mappers;

import com.devbridge.learning.Apptasks.dtos.EmployeeNameAndImageDto;
import com.devbridge.learning.Apptasks.dtos.TaskDetailedDto;
import com.devbridge.learning.Apptasks.dtos.TaskDetailedWithPhotosDto;
import com.devbridge.learning.Apptasks.dtos.TaskDto;
import com.devbridge.learning.Apptasks.exceptions.InvalidEnumValueException;
import com.devbridge.learning.Apptasks.models.*;

public class TaskMapper {
    public static TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }

        return TaskDto.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .categoryId(task.getCategory() != null ? task.getCategory().getCategoryId() : null)
                .description(task.getDescription())
                .createdById(task.getCreatedById())
                .assignedToId(task.getAssignedToId())
                .status(task.getStatus() != null ? task.getStatus().toString() : null)
                .priority(task.getPriority() != null ? task.getPriority().toString() : null)
                .projectId(task.getProjectId())
                .createdDate(task.getCreatedDate())
                .assignedDate(task.getAssignedDate())
                .unassignedDate(task.getUnassignedDate())
                .doneDate(task.getDoneDate())
                .build();
    }

    public static TaskDetailedDto toDetailedDto(Task task, String createdByFirstName, String createdByLastName, String assignedToFirstName, String assignedToLastName, String projectName) {
        if (task == null) {
            return null;
        }

        return TaskDetailedDto.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .categoryId(task.getCategory() != null ? task.getCategory().getCategoryId() : null)
                .description(task.getDescription())
                .createdById(task.getCreatedById())
                .createdByFirstName(createdByFirstName)
                .createdByLastName(createdByLastName)
                .assignedToId(task.getAssignedToId())
                .assignedToFirstName(assignedToFirstName)
                .assignedToLastName(assignedToLastName)
                .status(task.getStatus() != null ? task.getStatus().toString() : null)
                .priority(task.getPriority() != null ? task.getPriority().toString() : null)
                .projectId(task.getProjectId())
                .projectName(projectName)
                .createdDate(task.getCreatedDate())
                .assignedDate(task.getAssignedDate())
                .unassignedDate(task.getUnassignedDate())
                .doneDate(task.getDoneDate())
                .build();
    }

    public static TaskDetailedWithPhotosDto toDetailedWithPhotosDto(
            Task task,
            EmployeeNameAndImageDto createdByEmployee,
            EmployeeNameAndImageDto assignedToEmployee,
            String projectName
    ) {
        if (task == null) {
            return null;
        }

        return TaskDetailedWithPhotosDto.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .categoryId(task.getCategory() != null ? task.getCategory().getCategoryId() : null)
                .description(task.getDescription())
                .createdByEmployee(createdByEmployee)
                .assignedToEmployee(assignedToEmployee)
                .status(task.getStatus() != null ? task.getStatus().toString() : null)
                .priority(task.getPriority() != null ? task.getPriority().toString() : null)
                .projectId(task.getProjectId())
                .projectName(projectName)
                .createdDate(task.getCreatedDate())
                .assignedDate(task.getAssignedDate())
                .unassignedDate(task.getUnassignedDate())
                .doneDate(task.getDoneDate())
                .build();
    }

    public static Task toEntity(TaskDto taskDto, Category category) {
        return Task.builder()
                .taskId(taskDto.getTaskId())
                .title(taskDto.getTitle())
                .category(category)
                .description(taskDto.getDescription())
                .createdById(taskDto.getCreatedById())
                .assignedToId(taskDto.getAssignedToId())
                .status(toStatus(taskDto.getStatus()))
                .priority(toPriority(taskDto.getPriority()))
                .projectId(taskDto.getProjectId())
                .createdDate(taskDto.getCreatedDate())
                .assignedDate(taskDto.getAssignedDate())
                .unassignedDate(taskDto.getUnassignedDate())
                .doneDate(taskDto.getDoneDate())
                .build();
    }

    public static Status toStatus(String statusStr) {
        try {
            return statusStr != null ? Status.fromString(statusStr) : null;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidEnumValueException("status", statusStr);
        }
    }


    public static Priority toPriority(String priorityStr) {
        try {
            return priorityStr != null ? Priority.fromString(priorityStr) : null;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidEnumValueException("priority", priorityStr);
        }
    }
}
