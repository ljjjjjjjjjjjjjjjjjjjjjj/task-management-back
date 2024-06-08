package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.AssignmentDto;
import com.devbridge.learning.Apptasks.models.Assignment;
import com.devbridge.learning.Apptasks.services.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping
    public List<AssignmentDto> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/{assignmentId}")
    public AssignmentDto getAssignmentById(@PathVariable UUID assignmentId) {
        return assignmentService.getAssignmentById(assignmentId);
    }

    @PostMapping
    public void createAssignment(@RequestBody AssignmentDto assignmentDto) {
        assignmentService.createAssignment(assignmentDto);
    }

    @PutMapping("/{assignmentId}")
    public void updateAssignment(@PathVariable UUID assignmentId, @RequestBody AssignmentDto assignmentDto) {
        assignmentService.updateAssignment(assignmentId, assignmentDto);
    }

    @DeleteMapping("/{assignmentId}")
    public void deleteAssignment(@PathVariable UUID assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
    }
}