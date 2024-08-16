package com.wsa.model;

import lombok.Data;

@Data
public class Organizer {
    private Long id;

    private Long userId;
    private String organizationName;
    private String organizationDescription;

    // Getters and setters
}
