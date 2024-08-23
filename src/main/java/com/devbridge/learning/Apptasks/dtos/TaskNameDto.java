package com.devbridge.learning.Apptasks.dtos;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class TaskNameDto {
    private UUID taskId;
    private String title;
    private UUID assignedToId;
    private String status;
    private String priority;
}