package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class EventRequest implements Serializable {
    private static final long serialVersionUID = 6025313745577522163L;
    private Long eventId;
    private Long organizerId;
    private String title;
    private String description;
    private String location;
    private String status;
    private Integer pointsAwarded;
    private Timestamp startDate;
    private Timestamp endDate;
    private List<RoleQuantity> roles;
    private List<String> nearbyFacilities;
    private List<Long> fileIds;
    private String eventPic;
    @Data
    public static class RoleQuantity {
        private String role;
        private Integer quantity;

    }
}