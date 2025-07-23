package com.project.model.vo;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class DiaryVO {
    private Long diaryId;
    private Long userId;
    private String title;
    private String content;
    private String status;
    private Date createTime;
    private Date updateTime;
    private List<String> tags; // List of tag names
    private List<MediaVO> media; // List of media objects
} 