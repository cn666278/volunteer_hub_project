package com.wsa.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CountDataRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String icon;
    private String color;
    private Integer value;
}
