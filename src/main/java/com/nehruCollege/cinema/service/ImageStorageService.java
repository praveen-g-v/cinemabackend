package com.nehruCollege.cinema.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import com.nehruCollege.cinema.exception.ServiceException;

@Service
public class ImageStorageService {
    private final Path rootLocation;
    private final long maxFileSize;
    private final List<String> allowedContentTypes;

    @Autowired
    public ImageStorageService(
            @Value("${app.file.storage.location}") String uploadDir,
            @Value("${app.file.max-size}") DataSize maxFileSize,
            @Value("${app.file.allowed-types}") List<String> allowedContentTypes) throws ServiceException {
        
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.maxFileSize = maxFileSize.toBytes();
        this.allowedContentTypes = allowedContentTypes;
        
        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new ServiceException("Could not initialize storage");
        }
    }

    public String storeImage(MultipartFile file) throws ServiceException {
        // Validate the file
        if (file.isEmpty()) {
            throw new ServiceException("Failed to store empty file");
        }
        if (file.getSize() > maxFileSize) {
            throw new ServiceException("File size exceeds maximum limit");
        }
        if (!allowedContentTypes.contains(file.getContentType())) {
            throw new ServiceException("File type not allowed");
        }

        // Generate unique filename
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID() + fileExtension;

        try {
            // Check for path traversal attacks
            if (newFilename.contains("..")) {
                throw new ServiceException("Cannot store file with relative path");
            }

            // Copy file to target location
            Path targetLocation = this.rootLocation.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFilename;
        } catch (IOException e) {
            throw new ServiceException("Failed to store file " + newFilename);
        }
    }

    public Resource loadImage(String filename) throws ServiceException {
        try {
            Path filePath = this.rootLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ServiceException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new ServiceException("Could not read file: " + filename);
        }
    }

    public void deleteImage(String filename) throws ServiceException {
        try {
            Path filePath = this.rootLocation.resolve(filename).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new ServiceException("Failed to delete file: " + filename);
        }
    }
}