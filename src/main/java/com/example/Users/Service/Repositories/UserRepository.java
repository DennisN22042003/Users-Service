package com.example.Users.Service.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;

import com.example.Users.Service.Models.UserMetadata;

@Repository
public interface UserRepository extends MongoRepository<UserMetadata, String> {

    // Delete a user by ID
    void deleteById(String id);

    @Query("{ '_id' : ?0 }")
    Optional<UserMetadata> findById(String id);

    @Query("{ 'username' : ?0 }")
    Optional<UserMetadata> findByUsername(String username);
} 
