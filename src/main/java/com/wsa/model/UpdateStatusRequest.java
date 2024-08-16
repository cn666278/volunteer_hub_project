package com.wsa.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateStatusRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String status;
    private String email;

    private Long eventId;
}
