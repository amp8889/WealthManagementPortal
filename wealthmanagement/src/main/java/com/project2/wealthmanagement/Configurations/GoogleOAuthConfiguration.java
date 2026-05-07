// package com.project2.wealthmanagement.Configurations;

// import java.io.IOException;
// import java.util.HashSet;
// import java.util.Optional;
// import java.util.Set;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
// import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
// import org.springframework.security.oauth2.core.oidc.user.OidcUser;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
// import org.springframework.session.web.http.CookieSerializer;
// import org.springframework.session.web.http.DefaultCookieSerializer;
// import org.springframework.session.web.http.DefaultCookieSerializer;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;

// import com.project2.wealthmanagement.Enums.UserRole;
// import com.project2.wealthmanagement.Services.UserService;
// import com.project2.wealthmanagement.Models.User;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // ← servlet, not reactive

// @Configuration
// @EnableWebSecurity
// @Profile("!dev")
// public class GoogleOAuthConfiguration {

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private CorsConfigurationSource corsConfigurationSource;

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//                 .sessionManagement(session -> session
//                         .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
//                 .cors(cors -> cors.configurationSource(corsConfigurationSource))
//                 .csrf(Customizer.withDefaults())
//                 .headers(headers -> headers
//                         .contentTypeOptions(Customizer.withDefaults())
//                         .frameOptions(frameOptions -> frameOptions.deny())
//                         .xssProtection((xss) -> xss
//                                 .headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
//                         .httpStrictTransportSecurity((hsts) -> hsts
//                                 .includeSubDomains(true)
//                                 .preload(true)
//                                 .maxAgeInSeconds(31536000)))
//                 .authorizeHttpRequests((authorize) -> authorize
//     .requestMatchers(
//         "/",
//         "/index.html",
//         "/favicon.ico",
//         "/**/*.js",      
//         "/**/*.css",
//         "/**/*.ico",
//         "/**/*.txt",
//         "/**/*.map",
//         "/**/*.woff",
//         "/**/*.woff2",
//         "/**/*.ttf",
//         "/**/*.png",
//         "/**/*.jpg",
//         "/**/*.svg",
//         "/media/**",
//         "/assets/**"
//     ).permitAll()
//                         .requestMatchers("/register")
//                         .hasRole("UNREGISTERED")
//                         .requestMatchers(HttpMethod.PUT, "/api/register")
//                         .hasRole("UNREGISTERED")
//                         .requestMatchers(HttpMethod.POST, "/api/clientrecords")
//                         .hasRole("UNREGISTERED")
//                         .requestMatchers(HttpMethod.POST, "/api/advisors")
//                         .hasRole("UNREGISTERED")
//                         .requestMatchers("/**")
//                         .hasAnyRole("ADMIN", "ADVISOR", "AUDITOR", "CLIENT"))
//                 .exceptionHandling(exceptions -> exceptions
//                         .authenticationEntryPoint((request, response, authException) -> {
//                             String uri = request.getRequestURI();
//                             if (uri.startsWith("/api/")) {
//                                 // API calls get a 401 so Angular can handle it
//                                 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//                             } else {
//                                 // Page requests get redirected to Google OAuth
//                                 response.sendRedirect("/oauth2/authorization/google");
//                             }
//                         }))
//                 .oauth2Login(oauth2 -> oauth2
//                         .userInfoEndpoint(endpoint -> endpoint
//                                 .oidcUserService(oidcUserService()))
//                         .successHandler(oauth2SuccessHandler()));

//         return http.build();
//     }

//     // Load role for RBAC
//     @Bean
//     public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
//         OidcUserService delegate = new OidcUserService();

//         return (userRequest) -> {
//             OidcUser oidcUser = delegate.loadUser(userRequest);
//             Set<GrantedAuthority> authorities = new HashSet<>(oidcUser.getAuthorities());
//             Optional<User> optCurrentUser = userService.findByGoogleId(oidcUser.getName());

//             if (optCurrentUser.isPresent()) {
//                 User user = optCurrentUser.get();

//                 authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

//             }

//             return new org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser(
//                     authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

//         };
//     }

//     @Bean
//     public AuthenticationSuccessHandler oauth2SuccessHandler() {
//         return new AuthenticationSuccessHandler() {
//             @Override
//             public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                     Authentication authentication) throws IOException {
//                 OAuth2User principal = (OAuth2User) authentication.getPrincipal();

//                 User user = userService.updateOrCreateGoogleUser(
//                         principal.getName(), // googleId
//                         principal.getAttribute("email"), // email
//                         principal.getAttribute("given_name"), // firstName
//                         principal.getAttribute("family_name"), // lastName
//                         principal.getAttribute("picture") // avatarUrl
//                 );
//                 if (user.getRole() == UserRole.UNREGISTERED) {
//                     response.sendRedirect("http://wealthmanagementportal.eastus.cloudapp.azure.com/register");
//                 } else {
//                     response.sendRedirect("http://wealthmanagementportal.eastus.cloudapp.azure.com");
//                 }
//             };
//         };

//     }

//     @Bean
//     public CookieSerializer cookieSerializer() {
//         DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//         serializer.setSameSite("Lax");
//         serializer.setUseSecureCookie(false); // set true once you have HTTPS
//         return serializer;
//     }

// }
