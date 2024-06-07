package com.devbridge.learning.Apptasks.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
    private UUID assignmentId;
    private String title;
    private String category;
    private String description;
    private UUID employeeId;

}
