package com.wsa.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = -389786612345350685L;
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
}
