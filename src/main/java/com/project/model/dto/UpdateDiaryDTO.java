package com.project.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdateDiaryDTO {
    private Long diaryId;
    private String title;
    private String content;
    private List<String> tags; // A list of tag names
    private List<Long> tagIds; // 新增，标签id数组
    private List<MultipartFile> newMediaFiles;
    private List<Long> mediaIdsToDelete; // IDs of media files to be removed
    private String status; // 草稿/已发布

} 