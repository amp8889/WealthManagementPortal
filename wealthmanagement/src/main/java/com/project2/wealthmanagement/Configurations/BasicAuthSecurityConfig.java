package com.project2.wealthmanagement.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfigurationSource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
@Configuration
@EnableWebSecurity
public class BasicAuthSecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public BasicAuthSecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;}
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // 🚫 NEVER redirect to login page
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(unauthorizedEntryPoint())
            )

            // APIs don't need CSRF
            .csrf(csrf -> csrf.disable())

            // allow Angular frontend
            .cors(cors -> cors.configurationSource(corsConfigurationSource))

            // stateless API auth
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/**/*.js",
                    "/**/*.css",
                    "/assets/**"
                ).permitAll()

.requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                .requestMatchers("/api/**").authenticated()

                .anyRequest().authenticated()
            )

            // 🔐 BASIC AUTH ONLY (no OAuth anywhere)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * 🔥 CRITICAL FIX:
     * Prevent Spring from redirecting to login pages or OAuth flows.
     * Instead return 401 so Angular handles it.
     */
    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Unauthorized\"}");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}