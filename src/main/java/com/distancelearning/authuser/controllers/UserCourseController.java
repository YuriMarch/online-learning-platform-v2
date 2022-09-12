package com.distancelearning.authuser.controllers;

import com.distancelearning.authuser.clients.UserClient;
import com.distancelearning.authuser.dtos.CourseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users/{userId}/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class UserCourseController {

    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(@PathVariable UUID userId,
                                                               @PageableDefault(page = 0, size = 5, sort = "courseId",
                                                      direction = Sort.Direction.ASC) Pageable pageable){


        return ResponseEntity.status(HttpStatus.OK).body(userClient.getAllCoursesByUser(userId, pageable));
    }
}
