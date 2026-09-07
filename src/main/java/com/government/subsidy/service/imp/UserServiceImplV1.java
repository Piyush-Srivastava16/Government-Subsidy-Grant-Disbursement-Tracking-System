package com.government.subsidy.service.imp;

import com.government.subsidy.entity.User;
import com.government.subsidy.repository.UserRepository;
import com.government.subsidy.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceV1")
public class UserServiceImplV1 implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplV1(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {

        if (userRepository
                .findByUsername(user.getUsername())
                .isPresent()) {

            throw new RuntimeException(
                    "Username already exists");
        }

        if (user.getRole() == null
                || user.getRole().isBlank()) {

            user.setRole("FIELD_OFFICER");
        }

        user.setRole(user.getRole().toUpperCase());

        // Encrypt password before saving
        user.setPassword(
                passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {

        User existingUser =
                userRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with id: " + id));

        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }

        if (user.getPassword() != null
                && !user.getPassword().isBlank()) {

            existingUser.setPassword(
                    passwordEncoder.encode(user.getPassword()));
        }

        if (user.getRole() != null
                && !user.getRole().isBlank()) {

            existingUser.setRole(
                    user.getRole().toUpperCase());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {

            throw new RuntimeException(
                    "User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }
}