package com.wsa.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventFacilities implements Serializable {
    private static final long serialVersionUID = -2095369192944343398L;

    private Long id;
    private Long eventId;
    private String name;
}
