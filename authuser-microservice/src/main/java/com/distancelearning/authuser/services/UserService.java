package com.distancelearning.authuser.services;

import com.distancelearning.authuser.models.User;
import com.distancelearning.authuser.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    Page<User> findAll(Specification<User> spec, Pageable pageable);

    Optional<User> findById(UUID userId);

    Optional<User> findByUserId(UUID userId);

    void delete(User user);

    User save(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String username);

    Page<User> findAllInstructors(SpecificationTemplate.UserSpec spec, Pageable pageable);

    User saveUser(User user);

    void deleteUser(User user);
    User updateUser(User user);
    User updatePassword(User user);
}
