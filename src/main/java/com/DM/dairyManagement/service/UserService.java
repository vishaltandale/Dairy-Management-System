package com.DM.dairyManagement.service;

import com.DM.dairyManagement.exception.BadRequestException;
import com.DM.dairyManagement.exception.ResourceNotFoundException;
import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.isActive() && passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
    
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    /**
     * Generate password reset token for a user
     */
    public String generatePasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        
        // Generate unique token
        String resetToken = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusHours(1); // Token valid for 1 hour
        
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(expiry);
        userRepository.save(user);
        
        return resetToken;
    }
    
    /**
     * Reset user password using token
     */
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
            .orElseThrow(() -> new BadRequestException("Invalid password reset token"));
        
        // Check if token is expired
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Password reset token has expired");
        }
        
        // Update password and clear token
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }
    
    /**
     * Validate if a reset token is valid
     */
    public boolean validateResetToken(String token) {
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isEmpty()) {
            return false;
        }
        
        User user = userOpt.get();
        return user.getResetTokenExpiry() != null && 
               user.getResetTokenExpiry().isAfter(LocalDateTime.now());
    }
}
