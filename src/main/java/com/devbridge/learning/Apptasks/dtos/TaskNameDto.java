package com.devbridge.learning.Apptasks.dtos;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskNameDto {
    private UUID taskId;
    private String title;
    private UUID assignedToId;
    private String status;
    private String priority;
}