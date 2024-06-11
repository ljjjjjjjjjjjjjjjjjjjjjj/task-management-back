package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.AssignmentDto;
import com.devbridge.learning.Apptasks.services.AssignmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AssignmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AssignmentService assignmentService;

    @InjectMocks
    private AssignmentController assignmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assignmentController).build();
    }

    @Test
    public void testGetAllAssignments() throws Exception {
        AssignmentDto assignmentDto = AssignmentDto.builder()
                .assignmentId(UUID.randomUUID())
                .title("Test Assignment")
                .build();

        when(assignmentService.getAllAssignments()).thenReturn(List.of(assignmentDto));

        mockMvc.perform(get("/assignments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(assignmentDto))));
    }

    @Test
    public void testGetAssignmentById() throws Exception {
        UUID assignmentId = UUID.randomUUID();
        AssignmentDto assignmentDto = AssignmentDto.builder()
                .assignmentId(assignmentId)
                .title("Test Assignment")
                .build();

        when(assignmentService.getAssignmentById(eq(assignmentId))).thenReturn(assignmentDto);

        mockMvc.perform(get("/assignments/{assignmentId}", assignmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(assignmentDto)));
    }

    @Test
    public void testCreateAssignment() throws Exception {
        AssignmentDto assignmentDto = AssignmentDto.builder()
                .assignmentId(UUID.randomUUID())
                .title("Test Assignment")
                .build();

        doNothing().when(assignmentService).createAssignment(any(AssignmentDto.class));

        mockMvc.perform(post("/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(assignmentDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateAssignment() throws Exception {
        UUID assignmentId = UUID.randomUUID();
        AssignmentDto assignmentDto = AssignmentDto.builder()
                .assignmentId(assignmentId)
                .title("Updated Assignment")
                .build();

        doNothing().when(assignmentService).updateAssignment(eq(assignmentId), any(AssignmentDto.class));

        mockMvc.perform(put("/assignments/{assignmentId}", assignmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(assignmentDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAssignment() throws Exception {
        UUID assignmentId = UUID.randomUUID();

        doNothing().when(assignmentService).deleteAssignment(eq(assignmentId));

        mockMvc.perform(delete("/assignments/{assignmentId}", assignmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}