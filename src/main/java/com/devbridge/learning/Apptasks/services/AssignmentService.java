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
    private final static String ASSIGNMENT_NOT_FOUND = "Assignment with given id not found";
    private static final String CATEGORY_NOT_FOUND = "Category with given id not found";

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

        if (assignmentDto.getStatus() == null) {
            assignmentDto.setStatus(Status.NOT_STARTED.toString());
        }
        if (assignmentDto.getPriority() == null) {
            assignmentDto.setPriority(Priority.MEDIUM.toString());
        }

        if (assignmentDto.getAssignedToId() != null) {
            assignmentDto.setAssignedDate(OffsetDateTime.now(ZoneOffset.UTC));
        } else {
            assignmentDto.setAssignedDate(null);
        }

        assignmentDto.setAssignmentId(UUID.randomUUID());
        assignmentDto.setCreatedDate(OffsetDateTime.now(ZoneOffset.UTC));

        Assignment assignment = AssignmentMapper.toEntity(assignmentDto, category);

        assignmentRepository.create(assignment);
    }

    public void updateAssignment(UUID assignmentId, AssignmentDto assignmentDto) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException(ASSIGNMENT_NOT_FOUND));

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

        if (assignmentDto.getAssignedToId() != null) {
            existingAssignment.setAssignedDate(OffsetDateTime.now(ZoneOffset.UTC));
        } else {
            existingAssignment.setAssignedDate(null);
        }

        existingAssignment.setTitle(assignmentDto.getTitle());
        existingAssignment.setDescription(assignmentDto.getDescription());
        existingAssignment.setCreatedById(assignmentDto.getCreatedById());
        existingAssignment.setAssignedToId(assignmentDto.getAssignedToId());
        existingAssignment.setCreatedDate(assignmentDto.getCreatedDate());
        existingAssignment.setUnassignedDate(assignmentDto.getUnassignedDate());
        existingAssignment.setDoneDate(assignmentDto.getDoneDate());

        assignmentRepository.update(existingAssignment);
    }

    public void deleteAssignment(UUID assignmentId) {
        assignmentRepository.delete(assignmentId);
    }
}