package com.project.controller;

import com.project.model.dto.CreateDiaryDTO;
import com.project.model.dto.QueryDiaryDTO;
import com.project.model.dto.UpdateDiaryDTO;
import com.project.model.vo.DiaryVO;
import com.project.service.DiaryService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    // Assuming we can get the user ID from the security context,
    // but for now, we'll pass it as a header or parameter for simplicity.
    // In a real app, this would be handled by Spring Security.
    private static final Long MOCK_USER_ID = 1L; // Placeholder for logged-in user

    @PostMapping
    public Result<DiaryVO> createDiary(@ModelAttribute CreateDiaryDTO createDiaryDTO) {
        // In a real implementation, the user ID would come from the security context
        return diaryService.createDiary(createDiaryDTO, MOCK_USER_ID);
    }

    @PutMapping("/{id}")
    public Result<DiaryVO> updateDiary(@PathVariable("id") Long diaryId, @ModelAttribute UpdateDiaryDTO updateDiaryDTO) {
        updateDiaryDTO.setDiaryId(diaryId);
        return diaryService.updateDiary(updateDiaryDTO);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteDiary(@PathVariable("id") Long diaryId) {
        return diaryService.deleteDiary(diaryId);
    }

    @DeleteMapping("/media/{mediaId}")
    public Result<Void> deleteDiaryMedia(@PathVariable("mediaId") Long mediaId) {
        return diaryService.deleteDiaryMedia(mediaId);
    }

    @GetMapping("/{id}")
    public Result<DiaryVO> getDiaryById(@PathVariable("id") Long diaryId) {
        return diaryService.getDiaryById(diaryId);
    }

    @GetMapping
    public Result<List<DiaryVO>> findDiaries(QueryDiaryDTO queryDiaryDTO) {
        // In a real implementation, the user ID would come from the security context
        return diaryService.findDiaries(queryDiaryDTO, MOCK_USER_ID);
    }
} 