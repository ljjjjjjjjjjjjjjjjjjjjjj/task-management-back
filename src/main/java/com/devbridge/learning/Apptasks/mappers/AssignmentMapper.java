package com.devbridge.learning.Apptasks.mappers;

import com.devbridge.learning.Apptasks.dtos.AssignmentDto;
import com.devbridge.learning.Apptasks.models.Assignment;

public class AssignmentMapper {
    public static AssignmentDto toDto(Assignment assignment) {
        if (assignment == null) {
            return null;
        }

        return AssignmentDto.builder()
                .assignmentId(assignment.getAssignmentId())
                .title(assignment.getTitle())
                .category(assignment.getCategory())
                .description(assignment.getDescription())
                .employeeId(assignment.getEmployeeId())
                .build();
    }

    public static Assignment toEntity(AssignmentDto assignmentDto) {
        if (assignmentDto == null) {
            return null;
        }

        return Assignment.builder()
                .assignmentId(assignmentDto.getAssignmentId())
                .title(assignmentDto.getTitle())
                .category(assignmentDto.getCategory())
                .description(assignmentDto.getDescription())
                .employeeId(assignmentDto.getEmployeeId())
                .build();
    }
}
