package com.wsa.model;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long eventId;
    private Long senderId;
    private String content;
    private LocalDateTime timestamp;
    @Transient
    private String username;
}
