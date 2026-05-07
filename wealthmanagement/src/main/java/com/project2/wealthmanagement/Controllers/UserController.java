package com.project2.wealthmanagement.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project2.wealthmanagement.Enums.UserRole;
import com.project2.wealthmanagement.Services.UserService;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = { "http://127.0.0.1:3000", "http://localhost:8080", "http://127.0.0.1:5500", "http://wealthmanagementportal.eastus.cloudapp.azure.com",
        "http://localhost:4200" })
@RestController
@RequestMapping("/api/register")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    @Profile("!dev")
    public String completeRegistration(
            @AuthenticationPrincipal OAuth2User oauth2User, @RequestParam UserRole role,
            @RequestParam(required = false) String relatedId) {

        String googleId = oauth2User.getAttribute("sub");

        userService.updateUserRoleAndRelatedId(googleId, role, relatedId);

        return "Registration complete! Role: " + role;
    }

    // @PutMapping
    // @Profile("dev")
    // public String completeRegistrationHardcodeDev() {

    //     userService.updateUserRoleAndRelatedId("GOOGLEID HERE", UserRole.valueOf("CLIENT"), "fa26bfc7-76fc-49d0-b99d-681f64a3438b");

    //     return "Hardcoded registration completed";
    // }
}
