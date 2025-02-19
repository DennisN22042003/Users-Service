package com.example.Users.Service.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users_metadata")
public class UserMetadata {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    // Add profile picture URL;
    // Add birthday

    // Constructors
    public UserMetadata() {

    }

    public UserMetadata(String username, String email, String id) {
        this.username = username;
        this.email = email;
        this.id = id;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
