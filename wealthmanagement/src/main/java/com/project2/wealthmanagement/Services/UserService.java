package com.project2.wealthmanagement.Services;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project2.wealthmanagement.Models.User;
import com.project2.wealthmanagement.Repositories.UserRepository;
@Service
public class UserService {


private final PasswordEncoder passwordEncoder;
private final UserRepository repository;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(String id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent())
            return user.get();
        return null;
    }

    public User createUser(User user) {
                if (user.getId() == null || user.getId().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));

        }
        return repository.save(user);
    }

    public User updateUser(String id, User user) {
        user.setId(id);
        return repository.save(user);
    }

    public boolean deleteUser(String id) {
        if (getUserById(id) == null) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }


public User getByEmail(String email) {
    return repository.findByEmail(email)
        .orElse(null);
}





}
