package com.project2.wealthmanagement.Configurations;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;

import com.project2.wealthmanagement.Services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class GoogleOAuthConfiguration {

    @Autowired
    private UserService userService;

    @Bean
    // @Profile("test")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oauth2SuccessHandler()));

        return http.build();
    }

    // @Bean
    // @Profile("dev")
    // public SecurityFilterChain devFilterChain(HttpSecurity http) throws Exception {
    //     http
    //             .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
    //             .csrf(csrf -> csrf.disable())
    //             .oauth2Login(oauth2 -> oauth2.disable());
    //     return http.build();
    // }

    public AuthenticationSuccessHandler oauth2SuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException {
                OAuth2User principal = (OAuth2User) authentication.getPrincipal();

                userService.updateOrCreateGoogleUser(
                        principal.getName(), // googleId
                        principal.getAttribute("email"), // email
                        principal.getAttribute("given_name"), // firstName
                        principal.getAttribute("family_name"), // lastName
                        principal.getAttribute("picture") // avatarUrl
                );
                response.sendRedirect("http://wealthmanagementportal.eastus.cloudapp.azure.com");
            }
        };
    }
}
