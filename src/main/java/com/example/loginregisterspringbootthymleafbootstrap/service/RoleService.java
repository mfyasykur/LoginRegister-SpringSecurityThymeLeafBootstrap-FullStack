package com.example.loginregisterspringbootthymleafbootstrap.service;

import com.example.loginregisterspringbootthymleafbootstrap.model.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findByName(String roleName);

    Role createRoleIfNotFound(String roleName);
}
