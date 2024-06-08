package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.EmployeeRegistrationDto;
import com.devbridge.learning.Apptasks.dtos.PasswordChangeDto;
import com.devbridge.learning.Apptasks.dtos.RegisterResponseDTO;
import com.devbridge.learning.Apptasks.exceptions.AuthenticationException;
import com.devbridge.learning.Apptasks.exceptions.RegistrationError;
import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Pattern COMPANY_EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@company\\.(com|[a-z]{2})$"
    );

    public RegisterResponseDTO registerUser(EmployeeRegistrationDto employeeRegistrationDto) {
        Map<String, Object> validationErrors = new HashMap<>();

        if (!COMPANY_EMAIL_PATTERN.matcher(employeeRegistrationDto.getEmail()).matches()) {
            validationErrors.put("email", RegistrationError.EMAIL_DOMAIN);
        }

        Optional<Employee> sameEmailEmployees = employeeRepository.findByEmail(employeeRegistrationDto.getEmail());
        if (sameEmailEmployees.isPresent()) {
            validationErrors.put("email", RegistrationError.EMAIL_EXISTS);
        }

        if (StringUtils.length(employeeRegistrationDto.getFirstName()) > 64) {
            validationErrors.put("firstName", RegistrationError.NAME_TOO_LONG);
        }
        if (StringUtils.length(employeeRegistrationDto.getLastName()) > 64) {
            validationErrors.put("lastName", RegistrationError.LAST_NAME_TOO_LONG);
        }
        if (StringUtils.length(employeeRegistrationDto.getEmail()) > 128) {
            validationErrors.put("email", RegistrationError.EMAIL_TOO_LONG);
        }
        if (!validationErrors.isEmpty()) {
            throw new AuthenticationException(validationErrors);
        }

        String hashedPassword = passwordEncoder.encode(employeeRegistrationDto.getPassword());

        Employee employee = Employee.builder()
                .employeeId(UUID.randomUUID())
                .firstName(employeeRegistrationDto.getFirstName())
                .lastName(employeeRegistrationDto.getLastName())
                .email(employeeRegistrationDto.getEmail())
                .password(hashedPassword)
                .build();

        employeeRepository.insert(employee);

        return new RegisterResponseDTO(employee.getEmployeeId(), "User registered successfully");
    }

    public void changePassword(PasswordChangeDto passwordChangeDto, String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        Employee employee = optionalEmployee.orElseThrow(()
                -> new AuthenticationException(Map.of("email", "User not found")));

        if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), employee.getPassword())) {
            throw new AuthenticationException(Map.of("oldPassword", "Old password is incorrect"));
        }

        employee.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        employeeRepository.update(employee);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        Employee employee = optionalEmployee.orElseThrow(()
                -> new UsernameNotFoundException("Employee not found with email: " + email));
        return new org.springframework.security.core.userdetails.User
                (employee.getEmail(), employee.getPassword(), Collections.emptyList());
    }

}