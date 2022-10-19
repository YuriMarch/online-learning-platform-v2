package com.distancelearning.authuser.controllers;

import com.distancelearning.authuser.clients.CourseClient;
import com.distancelearning.authuser.dtos.CourseDto;
import com.distancelearning.authuser.dtos.UserCourseDto;
import com.distancelearning.authuser.models.User;
import com.distancelearning.authuser.models.UserCourseModel;
import com.distancelearning.authuser.services.UserCourseService;
import com.distancelearning.authuser.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class UserCourseController {

    private final CourseClient courseClient;

    private final UserService userService;

    private final UserCourseService userCourseService;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(@PathVariable UUID userId,
                                                               @PageableDefault(page = 0, size = 5, sort = "courseId",
                                                                direction = Sort.Direction.ASC) Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));
    }

    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID userId, @RequestBody @Valid UserCourseDto userCourseDto){
        Optional<User> userOptional = userService.findById(userId);
        if(userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found");
        }

        if (userCourseService.existsByUserAndCourseId(userOptional.get(), userCourseDto.getCourseId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists.");
        }

        UserCourseModel userCourseModel = userCourseService.save(userOptional.get().convertToUserCourseModel(userCourseDto.getCourseId()));
        return ResponseEntity.status(HttpStatus.OK).body(userCourseModel);
    }

    @DeleteMapping("users/courses/{courseId}")
    public ResponseEntity<Object> deleteUserCourseByCourse(@PathVariable UUID courseId){
        if (!userCourseService.existsByCourseId(courseId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserCourse not found.");
        }
        userCourseService.deleteUserCourseByCourse(courseId);
        return ResponseEntity.status(HttpStatus.OK).body("UserCourse successfully deleted.");
    }
}
