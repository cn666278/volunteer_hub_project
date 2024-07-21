package com.wsa.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubmitCommentRequest implements Serializable {
    private static final long serialVersionUID = -3270534457356968273L;

    private Long eventId;
    private Long organizerId;
    private Long volunteerId;
    private int rating;
    private String comment;
}
