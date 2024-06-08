package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        List<EmployeeDto> employeeList = new ArrayList<>();
        EmployeeDto employeeDto = EmployeeDto.builder()
                .employeeId(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@company.com")
                .build();
        employeeList.add(employeeDto);

        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(employeeList)));
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        UUID employeeId = UUID.randomUUID();
        EmployeeDto employeeDto = EmployeeDto.builder()
                .employeeId(employeeId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@company.com")
                .build();

        when(employeeService.getEmployeeById(employeeId)).thenReturn(employeeDto);

        mockMvc.perform(get("/employees/{employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(employeeDto)));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        UUID employeeId = UUID.randomUUID();
        EmployeeDto employeeDto = EmployeeDto.builder()
                .employeeId(employeeId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@company.com")
                .build();

        doNothing().when(employeeService).updateEmployee(eq(employeeId), any(EmployeeDto.class));

        mockMvc.perform(put("/employees/{employeeId}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employeeDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        UUID employeeId = UUID.randomUUID();

        doNothing().when(employeeService).deleteEmployee(employeeId);

        mockMvc.perform(delete("/employees/{employeeId}", employeeId))
                .andExpect(status().isOk());
    }
}