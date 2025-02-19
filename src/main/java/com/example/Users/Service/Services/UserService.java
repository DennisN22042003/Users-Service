package com.example.Users.Service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Users.Service.Repositories.UserRepository;
import com.example.Users.Service.Models.UserMetadata;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public UserMetadata createUser(String username, String email) {
        // Save User Metadata in MongoDB
        UserMetadata metadata = new UserMetadata();
        metadata.setId(UUID.randomUUID().toString());
        metadata.setUsername(username);
        metadata.setEmail(email);

        // Save and return the new user
        UserMetadata savedUserMetadata = userRepository.save(metadata);
        return savedUserMetadata;
    }
    
    // Validate if a user exists
    public boolean isUserMetadataValid(String userId) {
        return userRepository.findById(userId).isPresent();
    }

    // Delete a user

    // Update user information
}
