package com.distancelearning.authuser.controllers;

import com.distancelearning.authuser.enums.UserType;
import com.distancelearning.authuser.models.User;
import com.distancelearning.authuser.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/instructors")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class InstructorController {

    private final UserService userService;

    @PutMapping("/{userId}/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@PathVariable UUID userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found");
        } else {
            var user = userOptional.get();
            user.setUserType(UserType.INSTRUCTOR);
            user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(user);
            System.out.println("user set as instructor");
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }
}
