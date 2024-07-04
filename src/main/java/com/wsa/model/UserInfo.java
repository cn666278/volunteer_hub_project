package com.wsa.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String loginId;
    private String username;
    private String password;
    private String token;
    private String phone;
    private String email;
    private String photo;
    private Role role;

    @Data
    public static class Role implements Serializable {
        private static final long serialVersionUID = 1L;
        private String roleId;
        private String roleName;
    }
}
