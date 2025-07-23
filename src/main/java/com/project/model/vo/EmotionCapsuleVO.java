package com.project.model.vo;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class EmotionCapsuleVO {
    private Long capsuleId;
    private Date openTime;
    private Date createTime;
    private boolean isOpened;
    
    // These fields are only populated if isOpened is true
    private String content;
    private List<MediaVO> media;
} 