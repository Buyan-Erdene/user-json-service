package com.example.userjson.repository;

import com.example.userjson.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;  // JpaRepository → MongoRepository
import java.util.Optional;

public interface UserProfileRepository extends MongoRepository<UserProfile, String> {  // Long → String
    Optional<UserProfile> findByUsername(String username);
}