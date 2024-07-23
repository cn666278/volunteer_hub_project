package com.wsa.model;

import lombok.Data;

@Data
public class RedeemRequest {
    private Long userId;
    private Long itemId;
}
