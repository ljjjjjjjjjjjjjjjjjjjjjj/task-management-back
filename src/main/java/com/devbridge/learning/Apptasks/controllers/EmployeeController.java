package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/team/{teamId}")
    public List<EmployeeDto> getEmployeesByTeamId(@PathVariable UUID teamId) {
        return employeeService.getEmployeesByTeamId(teamId);
    }

    @GetMapping("/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable UUID employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public EmployeeDto updateEmployee(@PathVariable UUID employeeId, @RequestBody EmployeeDto employeeDto) {
        return employeeService.updateEmployee(employeeId, employeeDto);
    }

    @PutMapping("/role/add/{employeeId}")
    public EmployeeDto addEmployeeRole(@PathVariable UUID employeeId, @RequestParam int roleId) {
        return employeeService.addEmployeeRole(employeeId, roleId);
    }

    @PutMapping("/role/remove/{employeeId}")
    public EmployeeDto removeEmployeeRole(@PathVariable UUID employeeId, @RequestParam int roleId) {
        return employeeService.removeEmployeeRole(employeeId, roleId);
    }

    @PutMapping("/team/add/{employeeId}")
    public EmployeeDto addToTeam(@PathVariable UUID employeeId, @RequestParam UUID teamId) {
        return employeeService.addToTeam(employeeId, teamId);
    }

    @PutMapping("/team/remove/{employeeId}")
    public EmployeeDto removeFromTeam(@PathVariable UUID employeeId, @RequestParam UUID teamId) {
        return employeeService.removeFromTeam(employeeId, teamId);
    }


    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable UUID employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}