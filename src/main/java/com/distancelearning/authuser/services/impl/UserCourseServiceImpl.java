package com.distancelearning.authuser.services.impl;

import com.distancelearning.authuser.repositories.UserCourseRepository;
import com.distancelearning.authuser.services.UserCourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCourseServiceImpl implements UserCourseService {

    private final UserCourseRepository userCourseRepository;
}
