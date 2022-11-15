package com.distancelearning.course.services;

import com.distancelearning.course.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {
    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

    UserModel save(UserModel userModel);
}
