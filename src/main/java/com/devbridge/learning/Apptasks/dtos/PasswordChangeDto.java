package com.devbridge.learning.Apptasks.dtos;

import lombok.Data;

@Data
public class PasswordChangeDto {
    private String oldPassword;
    private String newPassword;
}