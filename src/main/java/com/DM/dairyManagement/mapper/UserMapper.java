package com.DM.dairyManagement.mapper;

import com.DM.dairyManagement.dto.UserRequest;
import com.DM.dairyManagement.dto.UserResponse;
import com.DM.dairyManagement.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    
    public User toEntity(UserRequest request) {
        User user = new User();
        user.setName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobileNumber());
        user.setPassword(request.getPassword()); // Will be encrypted in service
        user.setRole(User.Role.valueOf(request.getRole()));
        return user;
    }
    
    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFullName(user.getName());
        response.setEmail(user.getEmail());
        response.setMobileNumber(user.getMobile());
        response.setRole(user.getRole().name());
        response.setActive(user.isActive());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
    
    public List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
}
