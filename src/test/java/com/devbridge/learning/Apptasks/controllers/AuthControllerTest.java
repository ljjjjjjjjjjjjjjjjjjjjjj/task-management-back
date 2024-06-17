package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.dtos.EmployeeRegistrationDto;
import com.devbridge.learning.Apptasks.dtos.PasswordChangeDto;
import com.devbridge.learning.Apptasks.models.AuthRequest;
import com.devbridge.learning.Apptasks.models.AuthResponse;
import com.devbridge.learning.Apptasks.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testCreateAuthenticationToken() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("test@company.com");
        authRequest.setPassword("password1");

        EmployeeDto employeeDto = EmployeeDto.builder()
                .employeeId(UUID.randomUUID())
                .email("test@company.com")
                .firstName("Test")
                .lastName("User")
                .roles(Collections.emptySet())
                .build();

        AuthResponse authResponse = new AuthResponse("jwt-token", employeeDto);
        when(authService.login(any(AuthRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"jwt\":\"jwt-token\"}"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        EmployeeRegistrationDto registrationDto = EmployeeRegistrationDto.builder()
                .employeeId(UUID.randomUUID())
                .email("test@company.com")
                .password("password")
                .firstName("Test")
                .lastName("User")
                .build();

        EmployeeDto responseDto = EmployeeDto.builder()
                .employeeId(registrationDto.getEmployeeId())
                .email(registrationDto.getEmail())
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .build();

        when(authService.registerUser(any(EmployeeRegistrationDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registrationDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDto)));
    }

    @Test
    public void testChangePassword() throws Exception {
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        passwordChangeDto.setOldPassword("oldPassword");
        passwordChangeDto.setNewPassword("newPassword");

        mockMvc.perform(post("/auth/change-password")
                        .param("email", "test@company.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passwordChangeDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Password updated successfully"));
    }
}