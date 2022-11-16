package com.distancelearning.course.controllers;

import com.distancelearning.course.dtos.SubscriptionDto;
import com.distancelearning.course.enums.UserStatus;
import com.distancelearning.course.models.CourseModel;
import com.distancelearning.course.models.UserModel;
import com.distancelearning.course.services.CourseService;
import com.distancelearning.course.services.UserService;
import com.distancelearning.course.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class CourseUserController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getAllUsersInCourse(SpecificationTemplate.UserSpec spec,
                                                  @PageableDefault(size = 5, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                  @PathVariable UUID courseId){
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if(courseModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).
                body(userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                               @RequestBody @Valid SubscriptionDto subscriptionDto){
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);

        if(courseModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }

        if (courseService.existsByCourseAndUser(courseId, subscriptionDto.getUserId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
        }

        Optional<UserModel> userModelOptional = userService.findById(subscriptionDto.getUserId());
        if (userModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        if (userModelOptional.get().getUserStatus().equals(UserStatus.BLOCKED.toString())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked.");
        }
        courseService.saveSubscriptionUserInCourse(courseModelOptional.get().getCourseId(), userModelOptional.get().getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.");
    }
}
