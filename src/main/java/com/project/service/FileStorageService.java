package com.project.service;

import com.project.util.Result;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    /**
     * Stores a file and returns the access URL.
     * @param file The file to store.
     * @return Result containing the access URL or an error message.
     */
    Result<String> storeFile(MultipartFile file);
} 