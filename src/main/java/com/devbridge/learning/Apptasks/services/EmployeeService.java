package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.mappers.EmployeeMapper;
import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public static final String EMPLOYEE_NOT_FOUND = "Employee by the given id not found";

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(UUID employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Employee employee = optionalEmployee.orElseThrow(()
                -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        return EmployeeMapper.toDto(employee);
    }

    public void updateEmployee(UUID employeeId, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        existingEmployee.setFirstName(employeeDto.getFirstName());
        existingEmployee.setLastName(employeeDto.getLastName());
        existingEmployee.setEmail(employeeDto.getEmail());

        employeeRepository.update(existingEmployee);
    }

    public void deleteEmployee(UUID employeeId) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        employeeRepository.delete(existingEmployee.getEmployeeId());;
    }
}