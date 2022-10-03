package com.distancelearning.authuser.services.impl;

import com.distancelearning.authuser.models.User;
import com.distancelearning.authuser.models.UserCourseModel;
import com.distancelearning.authuser.repositories.UserCourseRepository;
import com.distancelearning.authuser.services.UserCourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserCourseServiceImpl implements UserCourseService {

    private final UserCourseRepository userCourseRepository;

    @Override
    public boolean existsByUserAndCourseId(User user, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(user, courseId);
    }

    @Override
    public UserCourseModel save(UserCourseModel userCourseModel) {
        return userCourseRepository.save(userCourseModel);
    }
}
