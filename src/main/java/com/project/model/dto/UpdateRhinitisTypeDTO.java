package com.project.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateRhinitisTypeDTO {
    private Long rid;
    private String rhinitisType;
    private String symptom;
    private String cause;
    private String treatmentMethod;
    private String notice;
    private MultipartFile imageFile; // 新增图片文件字段
    private String imageGuid; // 原有图片路径（用于删除旧图）
    private Integer isdelete;
}