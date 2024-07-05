package com.devbridge.learning.Apptasks.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    private UUID teamId;
    private String teamName;
    private UUID teamLeaderId;
}