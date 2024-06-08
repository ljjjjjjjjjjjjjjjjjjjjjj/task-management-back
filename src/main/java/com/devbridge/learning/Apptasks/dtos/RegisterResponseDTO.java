package com.devbridge.learning.Apptasks.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RegisterResponseDTO {
    private UUID employeeId;
    private String message;
}
