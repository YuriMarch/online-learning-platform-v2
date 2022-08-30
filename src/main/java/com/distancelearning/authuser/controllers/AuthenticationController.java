package com.distancelearning.authuser.controllers;

import com.distancelearning.authuser.dtos.UserDto;
import com.distancelearning.authuser.enums.UserStatus;
import com.distancelearning.authuser.enums.UserType;
import com.distancelearning.authuser.models.User;
import com.distancelearning.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                                   @JsonView(UserDto.UserView.RegistrationPost.class)
                                                   UserDto userDto){
        if (userService.existsByUsername(userDto.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already been used.");
        }
        if (userService.existsByEmail(userDto.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already been used.");
        }

        var user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.STUDENT);
        user.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
