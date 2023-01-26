package com.distancelearning.authuser.repositories;

import com.distancelearning.authuser.enums.RoleType;
import com.distancelearning.authuser.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByRoleName(RoleType roleName);
}
