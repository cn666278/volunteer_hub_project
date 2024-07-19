package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class EventReqByOrganizerId implements Serializable {
    private static final long serialVersionUID = 2333392037517217660L;

    private Long organizerId;
    private String title;
    private String status;
    private int page;
    private int size;
    public int getOffset() {
        return (page - 1) * size;
    }

    public int getLimit() {
        return size;
    }
}
