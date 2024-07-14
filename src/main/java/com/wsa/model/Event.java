package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
@Data
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long organizerId;
    private String title;
    private String description;
    private String location;
    private Timestamp startDate;
    private Timestamp endDate;
    private String status;
    private String notifications;
    private Integer pointsAwarded;

}
