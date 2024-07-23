package com.wsa.model;

import lombok.Data;

@Data
public class RedeemResponse {
    private boolean success;
    private String message;

    public RedeemResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
