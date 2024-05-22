package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(UUID employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.update(employee);
    }

    public void deleteEmployee(UUID employeeId) {
        employeeRepository.delete(employeeId);
    }
}