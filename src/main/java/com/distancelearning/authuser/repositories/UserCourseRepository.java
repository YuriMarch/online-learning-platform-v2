package com.distancelearning.authuser.repositories;

import com.distancelearning.authuser.models.User;
import com.distancelearning.authuser.models.UserCourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {

    boolean existsByUserAndCourseId(User User, UUID CourseId);
}
