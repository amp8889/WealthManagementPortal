package com.project2.wealthmanagement.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project2.wealthmanagement.Enums.UserRole;
import com.project2.wealthmanagement.Models.User;
import com.project2.wealthmanagement.Services.UserService;
import com.project2.wealthmanagement.Services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// @CrossOrigin(origins = { "http://127.0.0.1:3000", "http://localhost:8080", "http://127.0.0.1:5500", "http://wealthmanagementportal.eastus.cloudapp.azure.com",
//         "http://localhost:4200" })
@RestController
@RequestMapping("/api/user")
public class UserController {




    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }



    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(service.getUserById(id));
    }




    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(service.createUser(user));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
    user.setRole(UserRole.CLIENT); // 🔒 force safe role
    return ResponseEntity.ok(service.createUser(user));
}


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody User newUser) {
        User existingUser = service.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(service.updateUser(id, newUser), HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
