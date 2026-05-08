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
            
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(unauthorizedEntryPoint())
            )

            .csrf(csrf -> csrf.disable())

            .cors(cors -> cors.configurationSource(corsConfigurationSource))

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/favicon.ico",
                    "/**/*.js",
                    "/**/*.css",
                    "/assets/**"
                ).permitAll()

                .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()

                
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