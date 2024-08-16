package com.wsa.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class EventDataRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer today;
    private Integer monthly;
    private Integer total;
}
