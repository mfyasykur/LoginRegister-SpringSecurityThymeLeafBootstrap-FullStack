package com.example.loginregisterspringbootthymleafbootstrap.service;

import com.example.loginregisterspringbootthymleafbootstrap.dto.UserDto;
import com.example.loginregisterspringbootthymleafbootstrap.model.Role;
import com.example.loginregisterspringbootthymleafbootstrap.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    void registerNewUser(UserDto userDto, Set<Role> roles);

    User findByUsername(String username);

    List<UserDto> findAllUsers();

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
