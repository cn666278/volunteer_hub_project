package com.wsa.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = -1046677706500517977L;
    private String username;
    private String password;
}
