package com.distancelearning.authuser.services.impl;

import com.distancelearning.authuser.clients.CourseClient;
import com.distancelearning.authuser.enums.ActionType;
import com.distancelearning.authuser.models.User;
import com.distancelearning.authuser.publishers.UserEventPublisher;
import com.distancelearning.authuser.repositories.UserRepository;
import com.distancelearning.authuser.services.UserService;
import com.distancelearning.authuser.specifications.SpecificationTemplate;
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

    private final CourseClient courseClient;

    private final UserEventPublisher userEventPublisher;

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

    @Override
    public Optional<User> findByUserId(UUID userId) {return userRepository.findByUserId(userId);}

    @Transactional
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<User> findAllInstructors(SpecificationTemplate.UserSpec spec, Pageable pageable) {
        return userRepository.findAllInstructors(spec, pageable);
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        user = save(user);
        userEventPublisher.publishUserEvent(user.convertToUserEventDto(), ActionType.CREATE);
        return user;
    }

    @Transactional
    @Override
    public void deleteUser(User user) {
        delete(user);
        userEventPublisher.publishUserEvent(user.convertToUserEventDto(), ActionType.DELETE);
    }
    @Transactional
    @Override
    public User updateUser(User user) {
        user = save(user);
        userEventPublisher.publishUserEvent(user.convertToUserEventDto(), ActionType.UPDATE);
        return user;
    }

    @Override
    public User updatePassword(User user) {
        return save(user);
    }
}
