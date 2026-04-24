package com.DM.dairyManagement.dto;

import java.util.Map;

public record ApiResponse<T>(
    boolean success,
    T data,
    String message,
    Map<String, String> errors
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, "Operation successful", null);
    }
    
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message, null);
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, null, message, null);
    }
    
    public static <T> ApiResponse<T> error(String message, Map<String, String> errors) {
        return new ApiResponse<>(false, null, message, errors);
    }
}
