package com.example.Users.Service.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.example.Users.Service.Services.UserService;
import com.example.Users.Service.Models.UserMetadata;
import com.example.Users.Service.Repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // Endpoint for new user creation
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String email = payload.get("email");

        UserMetadata userMetadata = userService.createUser(username, email);
        String userId = userMetadata.getId();
        
        // Construct a response with userId
        Map<String, String> response = new HashMap<>();
        response.put("userId", userId);

        return ResponseEntity.ok(response);
    }

    // Endpoint to validate a user exists
    @GetMapping("validate/{userId}")
    public ResponseEntity<Boolean> validateUserMetadata(@PathVariable String userId) {
        boolean exists = userService.isUserMetadataValid(userId);
        return ResponseEntity.ok(exists);
    }

    // Endpoint to fetch a User by userId (not yet useable)
    @GetMapping("id/{userId}")
    public ResponseEntity<UserMetadata> getUserById(@PathVariable String userId) {
        return userRepository.findById(userId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to update a user's username
    @PatchMapping("/update/{userId}/username")
    public ResponseEntity<Void> updateUsername(@PathVariable String userId, @RequestBody Map<String, String> payload) {
        String newUsername = payload.get("username");
        if (newUsername == null || newUsername.trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); // Return 400 if username is missing or empty
        }
        try {
            userService.updateUsername(userId, newUsername);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Username already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Return 409 if username is already present
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if user not found
            }
        }
    }

    // Endpoint to fetch a User by username (not yet useable)
    @GetMapping("account/{username}")
    public ResponseEntity<UserMetadata> getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to add a Friend to a User
    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable String userId, @PathVariable String friendId) {
        userService.addFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }

    // Endpoint to remove a friend from a User
    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable String userId, @PathVariable String friendId) {
        userService.removeFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }

    // Endpoint to fetch a User's friends
    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<UserMetadata>> getFriends(@PathVariable String userId) {
        List<UserMetadata> friends = userService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }

    // Future work: Delete a user by Id & username
    
    // Future work: Update a user's information
}
