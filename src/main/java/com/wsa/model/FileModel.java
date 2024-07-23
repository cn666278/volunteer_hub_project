package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class FileModel implements Serializable {
    private static final long serialVersionUID = -6941831436937049065L;

    private Long id;
    private String filename;
    private String contentType;
    private byte[] data;
}
