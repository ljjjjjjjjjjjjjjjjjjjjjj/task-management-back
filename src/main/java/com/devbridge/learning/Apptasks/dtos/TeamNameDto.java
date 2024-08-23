package com.devbridge.learning.Apptasks.dtos;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class TeamNameDto {
    private UUID teamId;
    private String teamName;
}
