package com.project.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SliderDTO {
    private Long sliderId;
    private String title;
    private String imageUrl;
    private Integer isdeleted;
    private MultipartFile imageFile; // 新增图片文件字段
}
