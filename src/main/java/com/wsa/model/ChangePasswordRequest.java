package com.wsa.model;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private Long loginId;
    private String currentPassword;
    private String newPassword;

    // Getters and Setters
}
