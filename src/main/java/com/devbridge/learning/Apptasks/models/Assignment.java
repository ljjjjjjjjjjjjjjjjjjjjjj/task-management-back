package com.devbridge.learning.Apptasks.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Builder
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
