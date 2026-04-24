package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@Tag(name = "File Management", description = "APIs for file upload and download")
public class FileUploadController {
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Value("${app.file.upload-dir:./uploads}")
    private String uploadDir;
    
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
        "image/jpeg", "image/png", "image/gif", "image/webp",
        "application/pdf",
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    );
    
    @PostMapping("/upload")
    @Operation(summary = "Upload file", description = "Upload a single file")
    public ResponseEntity<ApiResponse<Map<String, String>>> uploadFile(
            @RequestParam("file") MultipartFile file) {
        
        // Validate content type
        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Invalid file type. Allowed: images, PDF, Word, Excel"));
        }
        
        try {
            String filename = fileStorageService.uploadFile(file);
            
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/download/")
                .path(filename)
                .toUriString();
            
            Map<String, String> response = Map.of(
                "filename", filename,
                "downloadUri", fileDownloadUri,
                "contentType", file.getContentType(),
                "size", String.valueOf(file.getSize())
            );
            
            return ResponseEntity.ok(ApiResponse.success(response, "File uploaded successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/upload/{subDirectory}")
    @Operation(summary = "Upload file to subdirectory", description = "Upload a file to a specific subdirectory")
    public ResponseEntity<ApiResponse<Map<String, String>>> uploadFileToDirectory(
            @RequestParam("file") MultipartFile file,
            @PathVariable String subDirectory) {
        
        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Invalid file type. Allowed: images, PDF, Word, Excel"));
        }
        
        try {
            String filename = fileStorageService.uploadFile(file, subDirectory);
            
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/download/")
                .path(filename)
                .toUriString();
            
            Map<String, String> response = Map.of(
                "filename", filename,
                "downloadUri", fileDownloadUri,
                "contentType", file.getContentType(),
                "size", String.valueOf(file.getSize())
            );
            
            return ResponseEntity.ok(ApiResponse.success(response, "File uploaded successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/download/{filename:.+}")
    @Operation(summary = "Download file", description = "Download a file by filename")
    public ResponseEntity<UrlResource> downloadFile(@PathVariable String filename) {
        try {
            Path filePath = fileStorageService.getFilePath(filename);
            UrlResource urlResource = new UrlResource(filePath.toUri());
            
            if (urlResource.exists()) {
                String contentType = "application/octet-stream";
                
                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(urlResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{filename:.+}")
    @Operation(summary = "Delete file", description = "Delete a file by filename")
    public ResponseEntity<ApiResponse<Void>> deleteFile(@PathVariable String filename) {
        try {
            fileStorageService.deleteFile(filename);
            return ResponseEntity.ok(ApiResponse.success(null, "File deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
        }
    }
}
