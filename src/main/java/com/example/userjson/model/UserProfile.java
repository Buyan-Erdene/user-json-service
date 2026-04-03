package com.example.userjson.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "user_profiles")  // @Entity → @Document
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserProfile {

    @Id
    private String id;  // Long → String (MongoDB ObjectId)

    @Indexed(unique = true)
    private String username;

    private String name;
    private String email;
    private String bio;
    private String phone;
    private String profileImageUrl; // Spaces-ийн зураг URL (Lab07)
}