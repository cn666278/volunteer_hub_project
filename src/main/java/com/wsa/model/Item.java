package com.wsa.model;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private String itemUrl;
    private String itemName;
    private String itemDescription;
    private int pointsRequired;
    private int stock;
}
