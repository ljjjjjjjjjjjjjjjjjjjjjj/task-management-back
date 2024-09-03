package com.devbridge.learning.Apptasks.dtos;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamNameDto {
    private UUID teamId;
    private String teamName;
}
