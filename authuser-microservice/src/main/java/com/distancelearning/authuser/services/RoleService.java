package com.distancelearning.authuser.services;

import com.distancelearning.authuser.enums.RoleType;
import com.distancelearning.authuser.models.RoleModel;

import java.util.Optional;

public interface RoleService {

    Optional<RoleModel> findByRoleName(RoleType roleName);
}
