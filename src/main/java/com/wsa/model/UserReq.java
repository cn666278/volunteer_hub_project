package com.wsa.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserReq implements Serializable {
    private static final long serialVersionUID = 546646069310680662L;

    private Long id;
    private Long loginId;
    private Long roleId;
    private String password;
    private Long roleName;
    private String username;
    private String phone;
    private String email;
    private String photo;
}
