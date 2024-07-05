package com.devbridge.learning.Apptasks.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class HealthCheckController {

    @GetMapping("/api/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Server is up and running");
    }
}