package com.devbridge.learning.Apptasks.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageEmployee {
    private UUID imageId;
    private String imageTitle;
    private byte[] imageData;
    private UUID employeeId;
}