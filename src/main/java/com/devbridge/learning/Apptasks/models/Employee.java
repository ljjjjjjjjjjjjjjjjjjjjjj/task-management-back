package com.devbridge.learning.Apptasks.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private UUID employeeId;
    private String name;
    private String email;
    private String password;
}
