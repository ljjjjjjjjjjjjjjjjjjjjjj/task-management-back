package com.devbridge.learning.Apptasks.mappers;

import com.devbridge.learning.Apptasks.dtos.TaskDto;
import com.devbridge.learning.Apptasks.exceptions.InvalidEnumValueException;
import com.devbridge.learning.Apptasks.models.Task;
import com.devbridge.learning.Apptasks.models.Category;
import com.devbridge.learning.Apptasks.models.Priority;
import com.devbridge.learning.Apptasks.models.Status;

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
            return statusStr != null ? Status.valueOf(statusStr.toUpperCase()) : null;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidEnumValueException("Invalid status value: " + statusStr);
        }
    }

    public static Priority toPriority(String priorityStr) {
        try {
            return priorityStr != null ? Priority.valueOf(priorityStr.toUpperCase()) : null;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidEnumValueException("Invalid priority value: " + priorityStr);
        }
    }
}
