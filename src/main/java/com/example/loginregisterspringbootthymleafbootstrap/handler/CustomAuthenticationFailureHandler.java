package com.example.loginregisterspringbootthymleafbootstrap.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            response.sendRedirect("/login?inactive=true");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }
}
