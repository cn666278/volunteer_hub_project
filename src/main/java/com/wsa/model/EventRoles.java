package com.wsa.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventRoles implements Serializable {
    private static final long serialVersionUID = 9057601931708020454L;
    private Long id;
    private Long eventId;
    private String roleName;
    private String roleDescription;
    private Integer volunteerCount;

}
