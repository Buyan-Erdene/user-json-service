package com.example.userjson.controller;

import com.example.userjson.client.SoapAuthClient;
import com.example.userjson.model.UserProfile;
import com.example.userjson.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")   // Frontend-ийн хүсэлтийг зөвшөөрнө
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileRepository repo;
    private final SoapAuthClient soapAuthClient;

    private ResponseEntity<?> checkAuth(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Token шаардлагатай");
        }
        String token = authHeader.substring(7);
        if (!soapAuthClient.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Token хүчингүй");
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestHeader(value = "Authorization", required = false) String auth) {

        ResponseEntity<?> authCheck = checkAuth(auth);
        if (authCheck != null) return authCheck;

        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", required = false) String auth) {

        ResponseEntity<?> authCheck = checkAuth(auth);
        if (authCheck != null) return authCheck;

        return repo.findById(id)
                   .map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody UserProfile profile,
            @RequestHeader(value = "Authorization", required = false) String auth) {

        ResponseEntity<?> authCheck = checkAuth(auth);
        if (authCheck != null) return authCheck;

        UserProfile saved = repo.save(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @RequestBody UserProfile updated,
            @RequestHeader(value = "Authorization", required = false) String auth) {

        ResponseEntity<?> authCheck = checkAuth(auth);
        if (authCheck != null) return authCheck;

        return repo.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            existing.setBio(updated.getBio());
            existing.setPhone(updated.getPhone());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", required = false) String auth) {

        ResponseEntity<?> authCheck = checkAuth(auth);
        if (authCheck != null) return authCheck;

        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}