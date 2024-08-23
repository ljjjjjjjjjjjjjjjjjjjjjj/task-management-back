package com.devbridge.learning.Apptasks.mappers;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.dtos.EmployeeNameDto;
import com.devbridge.learning.Apptasks.dtos.EmployeeRegistrationDto;
import com.devbridge.learning.Apptasks.models.Employee;

import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeMapper {
    public static EmployeeDto toDto(Employee employee) {
        if (employee == null) {
            return null;
        }

        return EmployeeDto.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .teamId(employee.getTeamId())
                .roles(employee.getRoles())
                .build();
    }

    public static Employee toEntity(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return null;
        }

        return Employee.builder()
                .employeeId(employeeDto.getEmployeeId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .teamId(employeeDto.getTeamId())
                .roles(employeeDto.getRoles())
                .build();
    }

    public static Employee toEntity(EmployeeRegistrationDto employeeRegistrationDto) {
        if (employeeRegistrationDto == null) {
            return null;
        }

        return Employee.builder()
                .employeeId(employeeRegistrationDto.getEmployeeId())
                .firstName(employeeRegistrationDto.getFirstName())
                .lastName(employeeRegistrationDto.getLastName())
                .email(employeeRegistrationDto.getEmail())
                .password(employeeRegistrationDto.getPassword())
                .build();
    }

    public static EmployeeNameDto toNameDto(Employee employee) {
        if (employee == null) {
            return null;
        }

        return EmployeeNameDto.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .build();
    }

    public static Set<EmployeeNameDto> toNameDtoSet(Set<Employee> employees) {
        if (employees == null) {
            return null;
        }
        return employees.stream()
                .map(EmployeeMapper::toNameDto)
                .collect(Collectors.toSet());
    }
}