package com.distancelearning.course.services.impl;

import com.distancelearning.course.models.UserModel;
import com.distancelearning.course.repositories.CourseRepository;
import com.distancelearning.course.repositories.UserRepository;
import com.distancelearning.course.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Transactional
    @Override
    public void delete(UUID userId) {
        courseRepository.deleteCourseUserByUser(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<UserModel> findById(UUID userInstructor) {
        return userRepository.findById(userInstructor);
    }
}
