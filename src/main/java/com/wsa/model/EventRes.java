package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class EventRes implements Serializable {
    private static final long serialVersionUID = -3563391820302790205L;
    private Long id;
    private String title;
    private String description;
    private String location;
    private Integer pointsAwarded;
    private Date startDate;
    private Date endDate;
    private String status;

}
=======
}
