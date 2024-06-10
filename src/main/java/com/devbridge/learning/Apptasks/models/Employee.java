package com.devbridge.learning.Apptasks.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> roles;
}