package com.example.loginregisterspringbootthymleafbootstrap.config;

import com.example.loginregisterspringbootthymleafbootstrap.model.Role;
import com.example.loginregisterspringbootthymleafbootstrap.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository) {
        return args -> {
            createRoleIfNotFound(roleRepository, "ROLE_USER");
            createRoleIfNotFound(roleRepository, "ROLE_ADMIN");
        };
    }

    private void createRoleIfNotFound(RoleRepository roleRepository, String roleName) {

        Role role = new Role();
        role.setName(roleName);

        roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(role));
    }
}
