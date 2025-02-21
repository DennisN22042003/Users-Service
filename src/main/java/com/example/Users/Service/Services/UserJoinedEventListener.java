package com.example.Users.Service.Services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import com.example.Users.Service.Models.UserMetadata;
import com.example.Users.Service.Repositories.UserRepository;
import com.example.Users.Service.Config.RabbitMQConfig;
import com.example.Users.Service.DTO.UserJoinedDTO;

@Service
public class UserJoinedEventListener {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveUseJoinedEvent(UserJoinedDTO userJoinedDTO) {
        // Log the received metadata (immediately after deserialization)
        if (userJoinedDTO == null) {
            System.out.println("❌ Deserialization failed: userJoinedDTO is null");
        } else {
            System.out.println("📩 Received User-Joined Event for User: " + userJoinedDTO.getUserId() + " for Event: " + userJoinedDTO.getEventId());
        }

        // Make sure userId and eventId are Strings (UUID)
        String userId = userJoinedDTO.getUserId();
        String eventId = userJoinedDTO.getEventId();

        // Find User by ID in the database
        Optional<UserMetadata> optionalEvent = userRepository.findById(userId);
        System.out.println("Looking for User with ID: " + userId);

        // Process if User is found
        if (optionalEvent.isPresent()) {
            UserMetadata userMetadata = optionalEvent.get();
            List<String> existingEventIds = userMetadata.getEventIds();

            boolean isFirstEventJoined = existingEventIds.isEmpty(); // Determine if it's the first Event a User is joining

            // Ensure eventIds list is initialized
            if (userMetadata.getEventIds() == null) {
                userMetadata.setEventIds(new ArrayList<>());
            }
            // Add the new eventId to the List
            userMetadata.getEventIds().add(userJoinedDTO.getEventId());

            // Save the updated user metadata with Event ID(s)
            userRepository.save(userMetadata);

            // Log whether it's the first Event joined or not
            if (isFirstEventJoined) {
                System.out.println("✅ First event ID added: " + userJoinedDTO.getEventId() + " for User: " + userJoinedDTO.getUserId());
            } else {
                System.out.println("✅ Additional Event ID(s) added: " + userJoinedDTO.getEventId() + " for User: " + userJoinedDTO.getUserId());
            }
        } else {
            System.out.println("❌ User not found: " + userId);
        }
    }
}
