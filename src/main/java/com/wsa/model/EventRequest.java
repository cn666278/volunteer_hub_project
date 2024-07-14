package com.wsa.model;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class EventRequest {

    private Long organizerId;
    private String title;
    private String description;
    private String location;
    private Integer pointsAwarded;
    private Timestamp startDate;
    private Timestamp endDate;
    private List<RoleQuantity> roles;
    private List<String> nearbyFacilities;

    @Data
    public static class RoleQuantity {
        private String role;
        private Integer quantity;

    }
}
