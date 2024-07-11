package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserRequest implements Serializable {
    private static final long serialVersionUID = 6710686126803728786L;

    private String username;
    private String loginId;
}