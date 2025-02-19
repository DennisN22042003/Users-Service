package com.example.Users.Service.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<Map<String, String>> createUser(@RequestParam String username, @RequestParam String email) {
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

    // Endpoint to fetch a User by userId
    @GetMapping("/{userId}")
    public ResponseEntity<UserMetadata> getUserById(@PathVariable String userId) {
        return userRepository.findById(userId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to fetch a User by username
    @GetMapping("/{username}")
    public ResponseEntity<UserMetadata> getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Future work: Delete a user by Id & username
    
    // Future work: Update a user's information
}
