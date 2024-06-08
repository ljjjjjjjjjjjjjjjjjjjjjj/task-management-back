package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable UUID employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public void updateEmployee(@PathVariable UUID employeeId, @RequestBody EmployeeDto employeeDto) {
        employeeService.updateEmployee(employeeId, employeeDto);
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable UUID employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}