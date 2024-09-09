package com.example.loginregisterspringbootthymleafbootstrap.config;

import com.example.loginregisterspringbootthymleafbootstrap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final RoleService roleService;

    @Autowired
    public DataInitializer(RoleService roleService) {
        this.roleService = roleService;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            roleService.createRoleIfNotFound("ROLE_USER");
            roleService.createRoleIfNotFound("ROLE_ADMIN");
        };
    }
}
