package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class EventRegistrations implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long eventId;
    private Long volunteerId;
    private Long roleId;
    private String status;
    private Timestamp registrationTime;
}
