package com.project2.wealthmanagement.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;

@Bean
public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
    return jwt -> {

        Collection<String> roles = new ArrayList<>();

        // Azure roles come from "roles" claim
        if (jwt.containsClaim("roles")) {
            roles.addAll(jwt.getClaimAsStringList("roles"));
        }

        // Convert to Spring format: ROLE_*
        var authorities = roles.stream()
                .map(role -> "ROLE_" + role)
                .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                .toList();

        return new JwtAuthenticationToken(jwt, authorities);
    };
}
