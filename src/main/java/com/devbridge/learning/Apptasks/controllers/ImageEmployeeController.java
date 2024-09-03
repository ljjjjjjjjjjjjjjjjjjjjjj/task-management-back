package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.TeamDto;
import com.devbridge.learning.Apptasks.models.ImageEmployee;
import com.devbridge.learning.Apptasks.dtos.ImageEmployeeRequest;
import com.devbridge.learning.Apptasks.services.ImageEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/images-employee")
@RequiredArgsConstructor
public class ImageEmployeeController {

    private final ImageEmployeeService imageEmployeeService;

    @PostMapping("/upload/{employeeId}")
    public ResponseEntity<ImageEmployee> uploadEmployeeImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @PathVariable UUID employeeId) {
        try {
            ImageEmployee employeeImage = imageEmployeeService.createEmployeeImage(file, title, employeeId);
            return ResponseEntity.ok(employeeImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ImageEmployee> getEmployeeImageByEmployeeId(@PathVariable UUID employeeId) {
        ImageEmployee imageEmployee = imageEmployeeService.getEmployeeImageByEmployeeId(employeeId);
        return imageEmployee != null ? ResponseEntity.ok(imageEmployee) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{imageIdId}")
    public ResponseEntity<ImageEmployee> getEmployeeImageByImageId(@PathVariable UUID imageIdId) {
        ImageEmployee imageEmployee = imageEmployeeService.getEmployeeImageByImageId(imageIdId);
        return imageEmployee != null ? ResponseEntity.ok(imageEmployee) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<ImageEmployee> getAllImages() {
        return imageEmployeeService.getAllImages();
    }

    @DeleteMapping("/{imageId}")
    public void deleteEmployeeImage(@PathVariable UUID imageId) {
        imageEmployeeService.deleteEmployeeImage(imageId);
    }
}