package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.dto.LoginRequest;
import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.service.EmailService;
import com.DM.dairyManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "APIs for user authentication and password management")
public class AuthRestController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailService emailService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(
            @RequestBody LoginRequest request,
            HttpSession session) {
        
        try {
            User user = userService.authenticate(request.email(), request.password());
            
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Invalid email or password"));
            }
            
            session.setAttribute("loggedInUser", user);
            session.setAttribute("userRole", user.getRole());
            
            Map<String, Object> responseData = Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail(),
                "role", user.getRole().name()
            );
            
            return ResponseEntity.ok(ApiResponse.success(responseData, "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Login failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(ApiResponse.success(null, "Logged out successfully"));
    }
    
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Not logged in"));
        }
        
        Map<String, Object> userData = Map.of(
            "id", user.getId(),
            "name", user.getName(),
            "email", user.getEmail(),
            "role", user.getRole().name()
        );
        
        return ResponseEntity.ok(ApiResponse.success(userData));
    }
    
    @PostMapping("/forgot-password")
    @Operation(summary = "Request password reset", description = "Send password reset email to user")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Email is required"));
        }
        
        try {
            // Generate reset token
            String resetToken = userService.generatePasswordResetToken(email);
            
            // Get user for email
            User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Send reset email
            emailService.sendPasswordResetEmail(email, resetToken, user.getName());
            
            return ResponseEntity.ok(ApiResponse.success(null, "Password reset email sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Failed to send reset email: " + e.getMessage()));
        }
    }
    
    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Reset password using valid token")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        
        if (token == null || newPassword == null) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Token and new password are required"));
        }
        
        if (newPassword.length() < 6) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Password must be at least 6 characters long"));
        }
        
        try {
            userService.resetPassword(token, newPassword);
            return ResponseEntity.ok(ApiResponse.success(null, "Password reset successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/validate-reset-token")
    @Operation(summary = "Validate reset token", description = "Check if password reset token is valid")
    public ResponseEntity<ApiResponse<Boolean>> validateResetToken(@RequestParam String token) {
        boolean isValid = userService.validateResetToken(token);
        return ResponseEntity.ok(ApiResponse.success(isValid));
    }
}
