package com.wsa.model;

import java.sql.Timestamp;

public class VolunteerInfo {

    private int informationId;
    private Integer volunteerId; // Use Integer to handle nullable fields
    private String infoTitle;
    private Integer eventId; // Use Integer to handle nullable fields
    private Timestamp sendTime;
    private String infoBody;

    // Default constructor
    public VolunteerInfo() {
    }

    // Parameterized constructor
    public VolunteerInfo(int informationId, Integer volunteerId, String infoTitle, Integer eventId, Timestamp sendTime, String infoBody) {
        this.informationId = informationId;
        this.volunteerId = volunteerId;
        this.infoTitle = infoTitle;
        this.eventId = eventId;
        this.sendTime = sendTime;
        this.infoBody = infoBody;
    }

    // Getters and Setters
    public int getInformationId() {
        return informationId;
    }

    public void setInformationId(int informationId) {
        this.informationId = informationId;
    }

    public Integer getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Integer volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public String getInfoBody() {
        return infoBody;
    }

    public void setInfoBody(String infoBody) {
        this.infoBody = infoBody;
    }

    @Override
    public String toString() {
        return "VolunteerInfo{" +
                "informationId=" + informationId +
                ", volunteerId=" + volunteerId +
                ", infoTitle='" + infoTitle + '\'' +
                ", eventId=" + eventId +
                ", sendTime=" + sendTime +
                ", infoBody='" + infoBody + '\'' +
                '}';
    }


}
