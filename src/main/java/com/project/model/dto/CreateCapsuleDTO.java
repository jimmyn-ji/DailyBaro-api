package com.project.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class CreateCapsuleDTO {
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date openTime;

    private String reminderType; // "app_notification" or "sms"

    private List<MultipartFile> mediaFiles;
} 