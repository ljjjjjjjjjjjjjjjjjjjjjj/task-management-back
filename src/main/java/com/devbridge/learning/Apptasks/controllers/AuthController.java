package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.dtos.EmployeeRegistrationDto;
import com.devbridge.learning.Apptasks.dtos.PasswordChangeDto;
import com.devbridge.learning.Apptasks.models.AuthRequest;
import com.devbridge.learning.Apptasks.models.AuthResponse;
import com.devbridge.learning.Apptasks.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        return authService.login(authRequest);
    }

    @PostMapping("/signup")
    public EmployeeDto registerUser(@RequestBody EmployeeRegistrationDto employeeRegistrationDto) {
        return authService.registerUser(employeeRegistrationDto);
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestBody PasswordChangeDto passwordChangeDto, @RequestParam String email) {
        authService.changePassword(passwordChangeDto, email);
        return "Password updated successfully";
    }
}