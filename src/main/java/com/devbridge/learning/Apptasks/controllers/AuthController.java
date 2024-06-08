package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.EmployeeRegistrationDto;
import com.devbridge.learning.Apptasks.dtos.PasswordChangeDto;
import com.devbridge.learning.Apptasks.dtos.RegisterResponseDTO;
import com.devbridge.learning.Apptasks.models.AuthRequest;
import com.devbridge.learning.Apptasks.models.AuthResponse;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.security.JwtUtil;
import com.devbridge.learning.Apptasks.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        final UserDetails userDetails = authService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return new AuthResponse(jwt);
    }

    @PostMapping("/signup")
    public RegisterResponseDTO registerUser(@RequestBody EmployeeRegistrationDto employeeRegistrationDto) {
        return authService.registerUser(employeeRegistrationDto);
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestBody PasswordChangeDto passwordChangeDto, @RequestParam String email) {
        authService.changePassword(passwordChangeDto, email);
        return "Password updated successfully";
    }
}