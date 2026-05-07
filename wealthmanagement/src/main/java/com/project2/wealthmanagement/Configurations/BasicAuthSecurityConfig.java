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

@Configuration
@EnableWebSecurity
public class BasicAuthSecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public BasicAuthSecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // no redirects, Angular handles auth
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(unauthorizedEntryPoint())
            )

            .csrf(csrf -> csrf.disable())

            .cors(cors -> cors.configurationSource(corsConfigurationSource))

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // public endpoints
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/favicon.ico",
                    "/**/*.js",
                    "/**/*.css",
                    "/assets/**"
                ).permitAll()

                .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()

                // 🔥 AUTH CHECK ENDPOINT
                .requestMatchers(HttpMethod.GET, "/api/user/me")
                    .hasAnyRole("ADMIN", "ADVISOR", "CLIENT")

                // 🔥 RBAC RULES

                // ADMIN only (full system access)
                .requestMatchers("/api/admin/**")
                    .hasRole("ADMIN")

                // ADVISOR + ADMIN (client data access)
                .requestMatchers("/api/clientrecords/**")
                    .hasAnyRole("ADMIN", "ADVISOR")

                .requestMatchers("/api/goal/**")
                    .hasAnyRole("ADMIN", "ADVISOR")

                // CLIENT dashboard only
                .requestMatchers("/api/client-dashboard/**")
                    .hasAnyRole("CLIENT", "ADMIN", "ADVISOR")

                // fallback
                .requestMatchers("/api/**")
                    .authenticated()

                .anyRequest()
                    .authenticated()
            )

            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

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