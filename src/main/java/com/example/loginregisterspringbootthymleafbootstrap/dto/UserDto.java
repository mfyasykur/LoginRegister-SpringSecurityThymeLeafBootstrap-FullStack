package com.example.loginregisterspringbootthymleafbootstrap.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "First Name must be filled")
    private String firstname;

    @NotBlank(message = "Last Name must be filled")
    private String lastname;

    private String fullname;

    @NotBlank(message = "Username should not be empty")
    private String username;

    @NotBlank(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
