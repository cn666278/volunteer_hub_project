package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserListRequest implements Serializable {
    private static final long serialVersionUID = -6980439034507326494L;

    private int pageIndex;
    private int pageSize;
    private int roleId;
}
