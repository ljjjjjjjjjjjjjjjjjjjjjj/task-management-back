package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.EmployeeRegistrationDto;
import com.devbridge.learning.Apptasks.dtos.RegisterResponseDTO;
import com.devbridge.learning.Apptasks.exceptions.AuthenticationException;
import com.devbridge.learning.Apptasks.models.AuthRequest;
import com.devbridge.learning.Apptasks.models.AuthResponse;
import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("test@company.com");
        authRequest.setPassword("password");

        Employee employee = new Employee();
        employee.setEmail("test@company.com");
        employee.setPassword("password");

        when(employeeRepository.findByEmail(authRequest.getEmail())).thenReturn(Optional.of(employee));
        when(jwtUtil.generateToken(any())).thenReturn("jwt-token");

        AuthResponse authResponse = authService.login(authRequest);

        assertNotNull(authResponse);
        assertEquals("jwt-token", authResponse.getJwt());
    }

    @Test
    void testRegisterUser_Success() {
        EmployeeRegistrationDto registrationDto = EmployeeRegistrationDto.builder()
                .email("test@company.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .build();

        when(employeeRepository.findByEmail(registrationDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registrationDto.getPassword())).thenReturn("hashed-password");

        RegisterResponseDTO responseDTO = authService.registerUser(registrationDto);

        assertNotNull(responseDTO);
        assertEquals("User registered successfully", responseDTO.getMessage());
        verify(employeeRepository, times(1)).insert(any(Employee.class));
    }

    @Test
    void testRegisterUser_EmailExists() {
        EmployeeRegistrationDto registrationDto = EmployeeRegistrationDto.builder()
                .email("test@company.com")
                .build();

        when(employeeRepository.findByEmail(registrationDto.getEmail())).thenReturn(Optional.of(new Employee()));

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            authService.registerUser(registrationDto);
        });

        assertTrue(exception.getValidationErrors().containsKey("email"));
        verify(employeeRepository, never()).insert(any(Employee.class));
    }
}