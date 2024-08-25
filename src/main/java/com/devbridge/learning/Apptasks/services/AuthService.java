package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.dtos.EmployeeRegistrationDto;
import com.devbridge.learning.Apptasks.dtos.PasswordChangeDto;
import com.devbridge.learning.Apptasks.exceptions.AuthenticationException;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.exceptions.RegistrationError;
import com.devbridge.learning.Apptasks.mappers.EmployeeMapper;
import com.devbridge.learning.Apptasks.models.AuthRequest;
import com.devbridge.learning.Apptasks.models.AuthResponse;
import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.models.Role;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.repositories.RoleRepository;
import com.devbridge.learning.Apptasks.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public static final String USER_NOT_FOUND = "User by the given id not found";
    public static final String DEFAULT_ROLE = "USER";
    public static final String DEFAULT_ROLE_NOT_FOUND = "Default Role not found";
    public static final String INVALID_CREDENTIALS = "Invalid username or password.";

    private static final Pattern COMPANY_EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@company\\.(com|[a-z]{2})$"
    );
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*[0-9@#$%^&+=!]).{8,64}$"
    );
    // ^                     - start of the string
    // (?= ... )             - a positive lookahead assertion (checks for the presence of something)
    // .                     - dot . matches any character (except newline)
    // *                     - asterisk * allows for zero or more occurrences of the preceding element
    // .*                    - any character, any number of times
    // [A-Za-z]        - at least one letter
    // [0-9@#$%^&+=!]  - at least one number or special character
    // {8,64}$                -  between 8 and 64 characters long

    public AuthResponse login(AuthRequest authRequest){
        Map<String, Object> validationErrors = new HashMap<>();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            validationErrors.put("error", INVALID_CREDENTIALS);
            throw new AuthenticationException(INVALID_CREDENTIALS, validationErrors);
        }

        final UserDetails userDetails = loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        Employee employee = employeeRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        EmployeeDto user = EmployeeMapper.toDto(employee);

        return new AuthResponse(jwt, user);
    }

    public EmployeeDto registerUser(EmployeeRegistrationDto employeeRegistrationDto) {
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
        if (!PASSWORD_PATTERN.matcher(employeeRegistrationDto.getPassword()).matches()) {
            validationErrors.put("password", RegistrationError.PASSWORD_REQUIREMENTS);
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

        Role defaultRole = roleRepository.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new EntityNotFoundException(DEFAULT_ROLE_NOT_FOUND));

        employee.setRoles(Set.of(defaultRole));

        employeeRepository.create(employee);
        employeeRepository.addRole(employee.getEmployeeId(), defaultRole.getRoleId());

        return EmployeeMapper.toDto(employee);
    }

    public void changePassword(PasswordChangeDto passwordChangeDto, String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        Employee employee = optionalEmployee.orElseThrow(()
                -> new AuthenticationException(Map.of("email", "User not found")));

        if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), employee.getPassword())) {
            throw new AuthenticationException(Map.of("oldPassword", "Old password is incorrect"));
        }

        if (passwordEncoder.matches(passwordChangeDto.getNewPassword(), employee.getPassword())) {
            throw new AuthenticationException(Map.of("newPassword", "New password must be different from the old password"));
        }

        if (!PASSWORD_PATTERN.matcher(passwordChangeDto.getNewPassword()).matches()) {
            throw new AuthenticationException(Map.of("newPassword", RegistrationError.PASSWORD_REQUIREMENTS));
        }

        employee.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        employeeRepository.update(employee);
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        Employee employee = optionalEmployee.orElseThrow(()
                -> new UsernameNotFoundException("Employee not found with email: " + email));

        Set<Role> roles = employee.getRoles();
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User
                (employee.getEmail(), employee.getPassword(), authorities);
    }

    public EmployeeDto getCurrentUser(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        return EmployeeMapper.toDto(employee);
    }

}