package com.distancelearning.authuser.services.impl;

import com.distancelearning.authuser.models.User;
import com.distancelearning.authuser.models.UserCourseModel;
import com.distancelearning.authuser.repositories.UserCourseRepository;
import com.distancelearning.authuser.repositories.UserRepository;
import com.distancelearning.authuser.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserCourseRepository userCourseRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    @Override
    public void delete(User user) {
        List<UserCourseModel> userCourseModelList = userCourseRepository.findAllUserCourseIntoUser(user.getUserId());
        if (!userCourseModelList.isEmpty()){
            userCourseRepository.deleteAll(userCourseModelList);
        }
        userRepository.delete(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
