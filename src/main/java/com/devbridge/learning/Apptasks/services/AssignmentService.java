package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.AssignmentDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.mappers.AssignmentMapper;
import com.devbridge.learning.Apptasks.models.Assignment;
import com.devbridge.learning.Apptasks.models.Category;
import com.devbridge.learning.Apptasks.models.Priority;
import com.devbridge.learning.Apptasks.models.Status;
import com.devbridge.learning.Apptasks.repositories.AssignmentRepository;
import com.devbridge.learning.Apptasks.repositories.CategoryRepository;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final CategoryRepository categoryRepository;
    private final EmployeeRepository employeeRepository;
    private final static String ASSIGNMENT_NOT_FOUND = "Assignment with given id not found";
    private static final String CATEGORY_NOT_FOUND = "Category with given id not found";
    private static final String EMPLOYEE_NOT_FOUND = "Employee with given id not found";

    public List<AssignmentDto> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(AssignmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public AssignmentDto getAssignmentById(UUID assignmentId) {
        return AssignmentMapper.toDto(assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException(ASSIGNMENT_NOT_FOUND)));
    }

    public void createAssignment(AssignmentDto assignmentDto) {
        Category category = null;
        if (assignmentDto.getCategoryId() != null) {
            category = categoryRepository.findById(assignmentDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
        }

        validateEmployeeId(assignmentDto.getCreatedById());
        if (assignmentDto.getAssignedToId() != null) {
            validateEmployeeId(assignmentDto.getAssignedToId());
        }

        if (assignmentDto.getStatus() == null) {
            assignmentDto.setStatus(Status.NOT_STARTED.toString());
        }
        if (assignmentDto.getPriority() == null) {
            assignmentDto.setPriority(Priority.MEDIUM.toString());
        }
        assignmentDto.setAssignmentId(UUID.randomUUID());
        assignmentDto.setCreatedDate(OffsetDateTime.now(ZoneOffset.UTC));

        Assignment assignment = AssignmentMapper.toEntity(assignmentDto, category);
        setAssignmentDatesOnCreate(assignment);

        assignmentRepository.create(assignment);
    }

    public void updateAssignment(UUID assignmentId, AssignmentDto assignmentDto) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException(ASSIGNMENT_NOT_FOUND));

        if (assignmentDto.getAssignedToId() != null) {
            validateEmployeeId(assignmentDto.getAssignedToId());
        }

        if (assignmentDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(assignmentDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
            existingAssignment.setCategory(category);
        } else {
            existingAssignment.setCategory(null);
        }

        if (assignmentDto.getStatus() != null) {
            existingAssignment.setStatus(AssignmentMapper.toStatus(assignmentDto.getStatus()));
        }

        if (assignmentDto.getPriority() != null) {
            existingAssignment.setPriority(AssignmentMapper.toPriority(assignmentDto.getPriority()));
        }

        setAssignmentDatesOnUpdate(existingAssignment, assignmentDto);

        existingAssignment.setTitle(assignmentDto.getTitle());
        existingAssignment.setDescription(assignmentDto.getDescription());
        existingAssignment.setAssignedToId(assignmentDto.getAssignedToId());

        assignmentRepository.update(existingAssignment);
    }

    public void deleteAssignment(UUID assignmentId) {
        assignmentRepository.delete(assignmentId);
    }

    private void setAssignmentDatesOnCreate(Assignment assignment) {
        if (assignment.getAssignedToId() != null) {
            assignment.setAssignedDate(OffsetDateTime.now(ZoneOffset.UTC));
        } else {
            assignment.setAssignedDate(null);
        }

        if (assignment.getStatus() == Status.DONE) {
            assignment.setDoneDate(OffsetDateTime.now(ZoneOffset.UTC));
        } else {
            assignment.setDoneDate(null);
        }
    }

    private void setAssignmentDatesOnUpdate(Assignment existingAssignment, AssignmentDto assignmentDto) {
        UUID existingId = existingAssignment.getAssignedToId();
        UUID newId = assignmentDto.getAssignedToId();

        if (newId != null) {
            if (!newId.equals(existingId)) {
                existingAssignment.setAssignedDate(OffsetDateTime.now(ZoneOffset.UTC));
            }
            existingAssignment.setUnassignedDate(null);
        }

        if (newId == null && existingId != null) {
            existingAssignment.setAssignedDate(null);
            existingAssignment.setUnassignedDate(OffsetDateTime.now(ZoneOffset.UTC));
        }

        if (existingAssignment.getStatus() == Status.DONE) {
            existingAssignment.setDoneDate(OffsetDateTime.now(ZoneOffset.UTC));
        } else {
            existingAssignment.setDoneDate(null);
        }
    }

    private void validateEmployeeId(UUID employeeId) {
        if (employeeRepository.findById(employeeId).isEmpty()) {
            throw new EntityNotFoundException(EMPLOYEE_NOT_FOUND);
        }
    }
}