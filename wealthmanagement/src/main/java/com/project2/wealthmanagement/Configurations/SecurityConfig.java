package com.project2.wealthmanagement.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(unauthorizedEntryPoint())
            )

            .csrf(csrf -> csrf.disable())

            .cors(cors -> cors.configurationSource(corsConfigurationSource))

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // Static frontend files
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/favicon.ico",
                    "/**/*.js",
                    "/**/*.css",
                    "/assets/**"
                ).permitAll()

                // Public endpoints
                .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()

                // Role-based endpoints
                .requestMatchers(HttpMethod.GET, "/api/user/me")
                    .hasAnyRole("ADMIN", "ADVISOR", "CLIENT")

                .requestMatchers("/api/admin/**")
                    .hasRole("ADMIN")

                .requestMatchers("/api/clientrecords/**")
                    .hasAnyRole("ADMIN", "ADVISOR", "CLIENT")

                .requestMatchers("/api/goal/**")
                    .hasAnyRole("ADMIN", "ADVISOR", "CLIENT")

                .requestMatchers("/api/dashboard/**")
                    .hasAnyRole("CLIENT", "ADMIN", "ADVISOR")

                .requestMatchers("/api/**")
                    .authenticated()

                .anyRequest()
                    .authenticated()
            )

            // 🔥 THIS IS THE IMPORTANT PART
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            );

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
}