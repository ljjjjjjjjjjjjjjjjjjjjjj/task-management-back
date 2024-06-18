package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.TaskDto;
import com.devbridge.learning.Apptasks.services.TaskService;
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

public class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetAllTasks() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .taskId(UUID.randomUUID())
                .title("Test Task")
                .build();

        when(taskService.getAllTasks()).thenReturn(List.of(taskDto));

        mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(taskDto))));
    }

    @Test
    public void testGetTaskById() throws Exception {
        UUID taskId = UUID.randomUUID();
        TaskDto taskDto = TaskDto.builder()
                .taskId(taskId)
                .title("Test Task")
                .build();

        when(taskService.getTaskById(eq(taskId))).thenReturn(taskDto);

        mockMvc.perform(get("/tasks/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(taskDto)));
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .taskId(UUID.randomUUID())
                .title("Test Task")
                .build();

        when(taskService.createTask(any(TaskDto.class))).thenReturn(taskDto);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTask() throws Exception {
        UUID taskId = UUID.randomUUID();
        TaskDto taskDto = TaskDto.builder()
                .taskId(taskId)
                .title("Updated Task")
                .build();

        when(taskService.updateTask(eq(taskId), any(TaskDto.class))).thenReturn(taskDto);

        mockMvc.perform(put("/tasks/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTask() throws Exception {
        UUID taskId = UUID.randomUUID();

        doNothing().when(taskService).deleteTask(eq(taskId));

        mockMvc.perform(delete("/tasks/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}