package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.models.Assignment;
import com.devbridge.learning.Apptasks.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment getAssignmentById(UUID AssignmentId) {
        return assignmentRepository.findById(AssignmentId);
    }

    public void createAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
    }

    public void updateAssignment(Assignment assignment) {
        assignmentRepository.update(assignment);
    }

    public void deleteAssignment(UUID AssignmentId) {
        assignmentRepository.delete(AssignmentId);
    }
}