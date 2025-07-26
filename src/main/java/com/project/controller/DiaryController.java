package com.project.controller;

import com.project.model.dto.CreateDiaryDTO;
import com.project.model.dto.QueryDiaryDTO;
import com.project.model.dto.UpdateDiaryDTO;
import com.project.model.vo.DiaryVO;
import com.project.service.DiaryService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @PostMapping
    public Result<DiaryVO> createDiary(@ModelAttribute CreateDiaryDTO createDiaryDTO, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        if (userId == null) {
            return Result.fail("用户未登录");
        }
        return diaryService.createDiary(createDiaryDTO, userId);
    }

    @PutMapping("/{id}")
    public Result<DiaryVO> updateDiary(@PathVariable("id") Long diaryId, @ModelAttribute UpdateDiaryDTO updateDiaryDTO, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        if (userId == null) {
            return Result.fail("用户未登录");
        }
        updateDiaryDTO.setDiaryId(diaryId);
        updateDiaryDTO.setUserId(userId); // 添加用户ID到DTO中
        return diaryService.updateDiary(updateDiaryDTO);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteDiary(@PathVariable("id") Long diaryId, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        if (userId == null) {
            return Result.fail("用户未登录");
        }
        return diaryService.deleteDiary(diaryId, userId);
    }

    @DeleteMapping("/media/{mediaId}")
    public Result<Void> deleteDiaryMedia(@PathVariable("mediaId") Long mediaId, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        if (userId == null) {
            return Result.fail("用户未登录");
        }
        return diaryService.deleteDiaryMedia(mediaId, userId);
    }

    @GetMapping("/{id}")
    public Result<DiaryVO> getDiaryById(@PathVariable("id") Long diaryId, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        if (userId == null) {
            return Result.fail("用户未登录");
        }
        return diaryService.getDiaryById(diaryId, userId);
    }

    @GetMapping
    public Result<List<DiaryVO>> findDiaries(QueryDiaryDTO queryDiaryDTO, @RequestParam(required = false) Long targetUserId, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        if (userId == null) {
            return Result.fail("用户未登录");
        }
        // 如果指定了targetUserId，则查询指定用户的日记
        if (targetUserId != null) {
            queryDiaryDTO.setTargetUserId(targetUserId);
        }
        return diaryService.findDiaries(queryDiaryDTO, userId);
    }

    /**
     * 从请求头中获取用户ID
     */
    private Long getUserIdFromRequest(HttpServletRequest request) {
        String uidHeader = request.getHeader("uid");
        if (uidHeader != null && !uidHeader.trim().isEmpty()) {
            try {
                return Long.parseLong(uidHeader.trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
} 