package com.example.loginregisterspringbootthymleafbootstrap.controller;

import com.example.loginregisterspringbootthymleafbootstrap.dto.UserDto;
import com.example.loginregisterspringbootthymleafbootstrap.model.Role;
import com.example.loginregisterspringbootthymleafbootstrap.model.User;
import com.example.loginregisterspringbootthymleafbootstrap.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String showHomePage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userService.findByUsername(currentUserName);

        model.addAttribute("user", currentUser);

        return "index";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        UserDto user = new UserDto();
        model.addAttribute("user", user);

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {

        // Validate if email or username already exists
        if (userService.existsByEmail(userDto.getEmail())) {
            result.rejectValue("email", "error.email", "There is already an account registered with that email.");
        }

        if (userService.existsByUsername(userDto.getUsername())) {
            result.rejectValue("username", "error.username", "There is already an account registered with that username.");
        }

        // If there is a validation error, return to the registration page
        if (result.hasErrors()) {
            return "register";
        }

        // Register new user
        try {
            Role role = new Role();
            role.setName("ROLE_USER");
            userService.registerNewUser(userDto, Set.of(role)); // Set default role as ROLE_USER
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.email", e.getMessage());
            result.rejectValue("username", "error.username", e.getMessage());

            return "register";
        }

        model.addAttribute("successMessage", "User registered successfully!");

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {

        return "login";
    }

    @GetMapping("/users")
    public String users(Model model) {

        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "users";
    }

    @RequestMapping("/404")
    public String notFound() {
        return "error/404";
    }
}
