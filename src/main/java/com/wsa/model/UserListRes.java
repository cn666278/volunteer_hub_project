package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserListRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long count;
    private List<UserInfo> list;
}
