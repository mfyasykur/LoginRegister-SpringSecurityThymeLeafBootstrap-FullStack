package com.example.loginregisterspringbootthymleafbootstrap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import static org.springframework.security.config.Customizer.withDefaults;

// This config is used for Full Stack Web Development with Spring Boot and Thymeleaf
// It is not used for the RESTful API with Spring Boot and Thymeleaf

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${remember.me.key}")
    private String rememberMeKey;

    private final UserDetailsService userDetailsService;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public SecurityConfig(UserDetailsService userDetailsService, AuthenticationFailureHandler authenticationFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(
                        csrf -> csrf
                                .ignoringRequestMatchers("/api/**")
                )
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/register", "/login", "/images/**", "/css/**", "/js/**", "/webjars/**").permitAll()
                                .requestMatchers("/users").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .failureHandler(authenticationFailureHandler)
                                .defaultSuccessUrl("/index", true)
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID", "remember-me")
                )
                .sessionManagement(
                        session -> session
                                .sessionFixation().migrateSession()
                                .invalidSessionUrl("/login")
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .maximumSessions(1)
                                .expiredUrl("/login?expired")
                )
                .exceptionHandling(
                        exception -> exception
                        .accessDeniedPage("/404")
                )
                .rememberMe(
                        rememberMe -> rememberMe
                        .key(rememberMeKey) // very secret & secure key
                        .tokenValiditySeconds(86400)
                )
                .headers(
                        headers -> headers
                        .xssProtection(withDefaults()) // Enable X-XSS-Protection header
                        .contentTypeOptions(withDefaults()) // Prevent MIME-type sniffing
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // Prevent clickjacking
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .maxAgeInSeconds(31536000) // 1 year
                        ) // to enforce the use of HTTPS and protect against man-in-the-middle attacks
                );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
