package com.DM.dairyManagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    
    @Value("${app.file.upload-dir:./uploads}")
    private String uploadDir;
    
    @Value("${app.file.max-size:5242880}") // 5MB default
    private long maxFileSize;
    
    /**
     * Upload a file and return the filename
     */
    public String uploadFile(MultipartFile file) {
        try {
            // Validate file
            validateFile(file);
            
            // Generate unique filename
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = getExtension(originalFilename);
            String newFilename = UUID.randomUUID().toString() + extension;
            
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Save file
            Path targetLocation = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            logger.info("File uploaded successfully: {}", newFilename);
            return newFilename;
            
        } catch (IOException e) {
            logger.error("Failed to upload file: {}", e.getMessage());
            throw new RuntimeException("Failed to upload file", e);
        }
    }
    
    /**
     * Upload file to specific subdirectory
     */
    public String uploadFile(MultipartFile file, String subDirectory) {
        try {
            validateFile(file);
            
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = getExtension(originalFilename);
            String newFilename = UUID.randomUUID().toString() + extension;
            
            Path uploadPath = Paths.get(uploadDir, subDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            Path targetLocation = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            logger.info("File uploaded to {}/{}", subDirectory, newFilename);
            return subDirectory + "/" + newFilename;
            
        } catch (IOException e) {
            logger.error("Failed to upload file: {}", e.getMessage());
            throw new RuntimeException("Failed to upload file", e);
        }
    }
    
    /**
     * Delete a file
     */
    public void deleteFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Files.deleteIfExists(filePath);
            logger.info("File deleted: {}", filename);
        } catch (IOException e) {
            logger.error("Failed to delete file: {}", e.getMessage());
            throw new RuntimeException("Failed to delete file", e);
        }
    }
    
    /**
     * Get file path
     */
    public Path getFilePath(String filename) {
        return Paths.get(uploadDir).resolve(filename).normalize();
    }
    
    /**
     * Validate file before upload
     */
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        
        if (file.getSize() > maxFileSize) {
            throw new RuntimeException("File size exceeds maximum allowed size: " + (maxFileSize / 1024 / 1024) + "MB");
        }
        
        // Check for path traversal attacks
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (filename.contains("..")) {
            throw new RuntimeException("Invalid filename: " + filename);
        }
    }
    
    /**
     * Get file extension
     */
    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex > 0 ? filename.substring(dotIndex) : "";
    }
}
