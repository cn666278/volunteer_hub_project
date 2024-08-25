package com.wsa.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long roleId;
    private String roleName;



}
