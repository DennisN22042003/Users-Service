package com.example.Users.Service.DTO;

import java.io.Serializable;

public class UserJoinedDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String eventId;
    private String userId;

    public UserJoinedDTO(String userId, String eventId) {
        this.eventId = eventId;
        this.userId = userId;
    }

    
    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
