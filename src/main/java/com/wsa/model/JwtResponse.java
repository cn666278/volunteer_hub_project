package com.wsa.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -7066747716041031517L;
    private final String jwtToken;
}
