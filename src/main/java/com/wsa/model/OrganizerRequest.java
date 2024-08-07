package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class OrganizerRequest implements Serializable {
    private static final long serialVersionUID = 6710686126803728786L;

    private Long organizerId;
}
