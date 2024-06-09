package com.devbridge.learning.Apptasks.mappers;

import com.devbridge.learning.Apptasks.dtos.AssignmentDto;
import com.devbridge.learning.Apptasks.exceptions.InvalidEnumValueException;
import com.devbridge.learning.Apptasks.models.Assignment;
import com.devbridge.learning.Apptasks.models.Category;
import com.devbridge.learning.Apptasks.models.Priority;
import com.devbridge.learning.Apptasks.models.Status;

public class AssignmentMapper {
    public static AssignmentDto toDto(Assignment assignment) {
        if (assignment == null) {
            return null;
        }

        return AssignmentDto.builder()
                .assignmentId(assignment.getAssignmentId())
                .title(assignment.getTitle())
                .categoryId(assignment.getCategory() != null ? assignment.getCategory().getCategoryId() : null)
                .description(assignment.getDescription())
                .createdById(assignment.getCreatedById())
                .status(assignment.getStatus() != null ? assignment.getStatus().toString() : null)
                .priority(assignment.getPriority() != null ? assignment.getPriority().toString() : null)
                .priority(assignment.getPriority().toString())
                .createdDate(assignment.getCreatedDate())
                .assignedDate(assignment.getAssignedDate())
                .unassignedDate(assignment.getUnassignedDate())
                .doneDate(assignment.getDoneDate())
                .build();
    }

    public static Assignment toEntity(AssignmentDto assignmentDto, Category category) {
        return Assignment.builder()
                .assignmentId(assignmentDto.getAssignmentId())
                .title(assignmentDto.getTitle())
                .category(category)
                .description(assignmentDto.getDescription())
                .createdById(assignmentDto.getCreatedById())
                .assignedToId(assignmentDto.getAssignedToId())
                .status(toStatus(assignmentDto.getStatus()))
                .priority(toPriority(assignmentDto.getPriority()))
                .createdDate(assignmentDto.getCreatedDate())
                .assignedDate(assignmentDto.getAssignedDate())
                .unassignedDate(assignmentDto.getUnassignedDate())
                .doneDate(assignmentDto.getDoneDate())
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
