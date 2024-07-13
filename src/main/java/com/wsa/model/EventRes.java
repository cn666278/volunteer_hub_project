package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class EventRes implements Serializable {
    private static final long serialVersionUID = -3563391820302790205L;
    private Long id;
    private String title;
    private Timestamp startDate;
    private Timestamp endDate;
}
