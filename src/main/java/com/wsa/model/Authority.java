package com.wsa.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Authority implements Serializable {
    private static final long serialVersionUID = 8949981331761935835L;
    private Long id;
    private String username;
    private Long roleId;
    private String authority;
}
