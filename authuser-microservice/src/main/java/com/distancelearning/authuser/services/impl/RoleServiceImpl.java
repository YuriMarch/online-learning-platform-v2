package com.distancelearning.authuser.services.impl;

import com.distancelearning.authuser.enums.RoleType;
import com.distancelearning.authuser.models.RoleModel;
import com.distancelearning.authuser.repositories.RoleRepository;
import com.distancelearning.authuser.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<RoleModel> findByRoleName(RoleType roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
