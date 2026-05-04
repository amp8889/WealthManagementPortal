package com.project2.wealthmanagement.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project2.wealthmanagement.Models.User;
import com.project2.wealthmanagement.Repositories.UserRepository;
import com.project2.wealthmanagement.Enums.UserRole;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public User updateOrCreateGoogleUser(String googleId, String email,
            String firstName, String lastName,
            String avatarUrl) {
        Optional<User> userByGoogleId = userRepository.findByGoogleId(googleId);

        if (userByGoogleId.isPresent()) {
            User user = userByGoogleId.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setAvatarUrl(avatarUrl);
            return userRepository.save(user);
        }
        else {
            User newUser = new User(
                    googleId,
                    UserRole.CLIENT, // Assuming new client sign up by default
                    null,
                    firstName,
                    lastName,
                    email,
                    avatarUrl);

            return userRepository.save(newUser);
        }
    }

    public Optional<User> findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}