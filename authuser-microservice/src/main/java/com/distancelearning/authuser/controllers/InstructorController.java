package com.distancelearning.authuser.controllers;

import com.distancelearning.authuser.enums.RoleType;
import com.distancelearning.authuser.enums.UserType;
import com.distancelearning.authuser.models.RoleModel;
import com.distancelearning.authuser.models.User;
import com.distancelearning.authuser.services.RoleService;
import com.distancelearning.authuser.services.UserService;
import com.distancelearning.authuser.specifications.SpecificationTemplate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/instructors")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class InstructorController {

    private final UserService userService;
    private final RoleService roleService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{userId}/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@PathVariable UUID userId) {
        Optional<User> userOptional = userService.findByUserId(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found");
        } else {
            RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_INSTRUCTOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            var user = userOptional.get();
            user.setUserType(UserType.INSTRUCTOR);
            user.getRoles().add(roleModel);
            user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public ResponseEntity<Page<User>> getAllInstructors(SpecificationTemplate.UserSpec spec,
                                         @PageableDefault(page = 0, size = 5,
                                                          direction = Sort.Direction.ASC) Pageable pageable) {
        var instructorPage = userService.findAllInstructors(spec, pageable);

        //Hateoas implementation
        if (!instructorPage.isEmpty()){
            for (User user : instructorPage.toList()){
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(instructorPage);
    }
}
