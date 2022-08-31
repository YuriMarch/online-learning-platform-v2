package com.distancelearning.authuser.repositories;

import com.distancelearning.authuser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
//    Page<User> findAll(Pageable pageable, Specification<User> spec);
}
