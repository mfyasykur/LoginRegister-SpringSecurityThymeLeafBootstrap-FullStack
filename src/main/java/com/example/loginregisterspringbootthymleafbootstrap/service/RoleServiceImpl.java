package com.example.loginregisterspringbootthymleafbootstrap.service;

import com.example.loginregisterspringbootthymleafbootstrap.model.Role;
import com.example.loginregisterspringbootthymleafbootstrap.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Transactional
    @Override
    public Role createRoleIfNotFound(String roleName) {
        return roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            return roleRepository.save(role);
        });
    }
}
