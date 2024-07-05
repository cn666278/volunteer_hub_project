package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class LoginResult implements Serializable {
    private static final long serialVersionUID = 6991862185057163492L;
    private String message;
    private String token;
}
