package com.example.Users.Service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Users.Service.Repositories.UserRepository;
import com.example.Users.Service.Models.UserMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public UserMetadata createUser(String username, String email, String firebaseUid) {
        // Save User Metadata in MongoDB
        UserMetadata metadata = new UserMetadata();
        metadata.setId(UUID.randomUUID().toString());
        metadata.setUsername(username);
        metadata.setEmail(email);
        metadata.setFirebaseUid(firebaseUid);

        // Save and return the new user
        UserMetadata savedUserMetadata = userRepository.save(metadata);
        return savedUserMetadata;
    }
    
    // Validate if a user exists
    public boolean isUserMetadataValid(String userId) {
        return userRepository.findById(userId).isPresent();
    }

    // Add a Friend for a User
    public void addFriend(String userId, String friendId) {
        UserMetadata user = userRepository.findById(userId).orElse(null);

        if (user != null && isUserMetadataValid(friendId)) {
            if (!user.getFriendIds().contains(friendId)) {
                user.getFriendIds().add(friendId);
                userRepository.save(user);
            }
        }
    }

    // Remove a Friend
    public void removeFriend(String userId, String friendId) {
        UserMetadata user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.getFriendIds().remove(friendId);
            userRepository.save(user);
        }
    }

    // Fetch all a User's friends
    public List<UserMetadata> getFriends(String userId) {
        UserMetadata user = userRepository.findById(userId).orElse(null);
        List<UserMetadata> friends = new ArrayList<>();
        if (user != null) {
            for(String friendId: user.getFriendIds()) {
                UserMetadata friend = userRepository.findById(friendId).orElse(null);
                if (friend != null) {
                    friends.add(friend);
                }
            }
        }
        return friends;
    }

    // Update a user's username
    public void updateUsername(String userId, String newUsername) {
        if (userRepository.findByUsername(newUsername).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        UserMetadata user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    // Update a user's firebase UID
    public void updateFirebaseUid(String userId, String newFirebaseUid) {
        UserMetadata user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFirebaseUid(newFirebaseUid);
        userRepository.save(user);
    }

    // Delete a user

    // Update user information
}
