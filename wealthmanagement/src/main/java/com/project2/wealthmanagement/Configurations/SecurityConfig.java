package com.project2.wealthmanagement.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt; // ← correct Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

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
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                                .decoder(jwtDecoder())))

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(unauthorizedEntryPoint()))
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/index.html", "/favicon.ico",
                                "/**/*.js", "/**/*.css", "/assets/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/me")
                        .hasAnyRole("ADMIN", "ADVISOR", "CLIENT")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/clientrecords/**")
                        .hasAnyRole("ADMIN", "ADVISOR", "CLIENT")
                        .requestMatchers("/api/goal/**")
                        .hasAnyRole("ADMIN", "ADVISOR", "CLIENT")
                        .requestMatchers("/api/dashboard/**")
                        .hasAnyRole("CLIENT", "ADMIN", "ADVISOR")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return (org.springframework.security.oauth2.jwt.Jwt jwt) -> { // ← fully qualified
            Collection<String> roles = new ArrayList<>();

            // Check for Azure AD roles claim (if app roles are configured in Azure AD)
            if (jwt.getClaims().containsKey("roles")) {
                roles.addAll(jwt.getClaimAsStringList("roles"));
            } else {
                // If no roles claim, grant default USER role (requires proper Azure AD app role
                // setup)
                // This allows tokens to proceed for role-based filtering in controllers
                roles.add("CLIENT");
            }

            var authorities = roles.stream()
                    .map(role -> "ROLE_" + role.toUpperCase())
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            return new JwtAuthenticationToken(jwt, authorities);
        };
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

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri(
                        "https://login.microsoftonline.com/f947665a-e85c-43ab-8198-2b8c8cbeb643/discovery/v2.0/keys")
                .build();
    }

}