package com.devbridge.learning.Apptasks.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageEmployeeRequest {
    private String title;
    private MultipartFile file;
}
