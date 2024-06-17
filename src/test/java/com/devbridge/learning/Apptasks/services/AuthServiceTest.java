package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.dtos.EmployeeRegistrationDto;
import com.devbridge.learning.Apptasks.exceptions.AuthenticationException;
import com.devbridge.learning.Apptasks.models.AuthRequest;
import com.devbridge.learning.Apptasks.models.AuthResponse;
import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.models.Role;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.repositories.RoleRepository;
import com.devbridge.learning.Apptasks.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private RoleRepository roleRepository;
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
        Role defaultRole = new Role(1, "USER");

        Employee employee = new Employee();
        employee.setEmail("test@company.com");
        employee.setPassword("password");
        employee.setRoles(Set.of(defaultRole));

        when(employeeRepository.findByEmail(authRequest.getEmail())).thenReturn(Optional.of(employee));
        when(jwtUtil.generateToken(any())).thenReturn("jwt-token");

        AuthResponse authResponse = authService.login(authRequest);

        assertNotNull(authResponse);
        assertEquals("jwt-token", authResponse.getJwt());
    }

    @Test
    void testRegisterUser_Success() {
        EmployeeRegistrationDto registrationDto = EmployeeRegistrationDto.builder()
                .employeeId(UUID.randomUUID())
                .email("test@company.com")
                .password("password1")
                .firstName("John")
                .lastName("Doe")
                .build();

        Employee employee = Employee.builder()
                .employeeId(UUID.randomUUID())
                .email("test@company.com")
                .firstName("John")
                .lastName("Doe")
                .password("password1")
                .build();

        Role defaultRole = new Role(1, "USER");

        when(employeeRepository.findByEmail(registrationDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registrationDto.getPassword())).thenReturn("password1");
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(defaultRole));

        EmployeeDto employeeDto = authService.registerUser(registrationDto);

        assertNotNull(employeeDto);
        assertEquals(registrationDto.getEmail(), employeeDto.getEmail());
        verify(employeeRepository, times(1)).create(any(Employee.class));
        verify(employeeRepository, times(1)).addRole(any(UUID.class), eq(defaultRole.getRoleId()));
    }

    @Test
    void testRegisterUser_EmailExists() {
        EmployeeRegistrationDto registrationDto = EmployeeRegistrationDto.builder()
                .employeeId(UUID.randomUUID())
                .email("test@company.com")
                .firstName("John")
                .lastName("Doe")
                .password("hashed-password")
                .build();

        when(employeeRepository.findByEmail(registrationDto.getEmail())).thenReturn(Optional.of(new Employee()));

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            authService.registerUser(registrationDto);
        });

        assertTrue(exception.getValidationErrors().containsKey("email"));
        verify(employeeRepository, never()).create(any(Employee.class));
    }
}