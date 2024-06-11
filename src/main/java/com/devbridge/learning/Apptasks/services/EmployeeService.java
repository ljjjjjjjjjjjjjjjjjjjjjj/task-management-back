package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.mappers.EmployeeMapper;
import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.models.Role;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;

    public static final String EMPLOYEE_NOT_FOUND = "Employee by the given id not found";
    public static final String ROLE_NOT_FOUND = "Role by the given id not found";
    public static final String ROLE_NEEDED = "Employee must have at least one role.";
    public static final String ROLE_ALREADY_ASSIGNED = "This role is already assigned to the employee";
    public static final String ROLE_NOT_ASSIGNED = "This role is not assigned to the employee";

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(UUID employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));
        return EmployeeMapper.toDto(employee);
    }

    public EmployeeDto updateEmployee(UUID employeeId, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        existingEmployee.setFirstName(employeeDto.getFirstName());
        existingEmployee.setLastName(employeeDto.getLastName());
        existingEmployee.setEmail(employeeDto.getEmail());

        employeeRepository.update(existingEmployee);
        return EmployeeMapper.toDto(existingEmployee);
    }

    public EmployeeDto addEmployeeRole(UUID employeeId, int roleId) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));

        if (existingEmployee.getRoles().contains(role)) {
            throw new IllegalArgumentException(ROLE_ALREADY_ASSIGNED);
        }

        employeeRepository.addRole(existingEmployee.getEmployeeId(), roleId);

        existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        return EmployeeMapper.toDto(existingEmployee);
    }

    public EmployeeDto removeEmployeeRole(UUID employeeId, int roleId) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));

        Set<Role> roles = existingEmployee.getRoles();
        if (!roles.contains(role)) {
            throw new IllegalArgumentException(ROLE_NOT_ASSIGNED);
        }

        roles.remove(role);
        if (roles.isEmpty()) {
            throw new IllegalArgumentException(ROLE_NEEDED);
        }

        employeeRepository.removeRole(existingEmployee.getEmployeeId(), roleId);

        existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        return EmployeeMapper.toDto(existingEmployee);
    }

    public void deleteEmployee(UUID employeeId) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        employeeRepository.delete(existingEmployee.getEmployeeId());
    }
}
