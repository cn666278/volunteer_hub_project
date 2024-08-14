package com.wsa.model;

import java.time.LocalDateTime;

public class VolunteerRating {

    private Long id;
    private Long eventId;
    private Long organizerId;
    private Long volunteerId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    // Default constructor
    public VolunteerRating() {
    }

    // Parameterized constructor
    public VolunteerRating(Long id, Long eventId, Long organizerId, Long volunteerId, Integer rating, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.eventId = eventId;
        this.organizerId = organizerId;
        this.volunteerId = volunteerId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Optional: Override toString() for better readability
    @Override
    public String toString() {
        return "VolunteerRating{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", organizerId=" + organizerId +
                ", volunteerId=" + volunteerId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
