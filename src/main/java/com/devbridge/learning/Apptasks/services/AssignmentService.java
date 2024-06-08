package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.AssignmentDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.mappers.AssignmentMapper;
import com.devbridge.learning.Apptasks.models.Assignment;
import com.devbridge.learning.Apptasks.repositories.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final static String ASSIGNMENT_NOT_FOUND = "Assignment with given id not found";

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
        Assignment assignment = AssignmentMapper.toEntity(assignmentDto);
        assignment.setAssignmentId(UUID.randomUUID());
        assignmentRepository.create(assignment);
    }

    public void updateAssignment(UUID assignmentId, AssignmentDto assignmentDto) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException(ASSIGNMENT_NOT_FOUND));

        existingAssignment.setTitle(assignmentDto.getTitle());
        existingAssignment.setCategory(assignmentDto.getCategory());
        existingAssignment.setDescription(assignmentDto.getDescription());
        existingAssignment.setEmployeeId(assignmentDto.getEmployeeId());

        assignmentRepository.update(existingAssignment);
    }

    public void deleteAssignment(UUID assignmentId) {
        assignmentRepository.delete(assignmentId);
    }
}