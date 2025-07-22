package com.project.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NursingProductDTO {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private MultipartFile imageFile;
    private String imageUrl;
    private boolean isdeleted;
    private Integer stock;
}
