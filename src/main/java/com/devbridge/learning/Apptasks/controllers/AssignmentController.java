package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.models.Assignment;
import com.devbridge.learning.Apptasks.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/{assignmentId}")
    public Assignment getAssignmentById(@PathVariable UUID assignmentId) {
        return assignmentService.getAssignmentById(assignmentId);
    }

    @PostMapping
    public void createAssignment(@RequestBody Assignment assignment) {
        assignmentService.createAssignment(assignment);
    }

    @PutMapping("/{assignmentId}")
    public void updateAssignment(@PathVariable UUID assignmentId, @RequestBody Assignment assignment) {
        assignment.setAssignmentId(assignmentId);
        assignmentService.updateAssignment(assignment);
    }

    @DeleteMapping("/{assignmentId}")
    public void deleteAssignment(@PathVariable UUID assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
    }
}