package com.example.Users.Service.DTO;

import java.io.Serializable;

public class UserJoinedDTO {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String eventId;

    public UserJoinedDTO(String userId, String eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUsedId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
