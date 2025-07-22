package com.project.model.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InsertRhinitisTypeDTO {
    private String rhinitisType;
    private String symptom;
    private String cause;
    private String treatmentMethod;
    private String notice;
    private String imageGuid;
    private MultipartFile imageFile; // 新增图片文件字段
}