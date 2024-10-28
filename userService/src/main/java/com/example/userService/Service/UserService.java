package com.example.userService.Service;

import com.example.userService.Entity.User;
import com.example.userService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
