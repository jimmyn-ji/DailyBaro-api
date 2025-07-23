package com.project.controller;

import com.project.model.dto.CreateCapsuleDTO;
import com.project.model.vo.EmotionCapsuleVO;
import com.project.service.EmotionCapsuleService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capsules")
public class EmotionCapsuleController {

    @Autowired
    private EmotionCapsuleService capsuleService;

    // Placeholder for the logged-in user ID
    private static final Long MOCK_USER_ID = 1L;

    @PostMapping
    public Result<EmotionCapsuleVO> createCapsule(@ModelAttribute CreateCapsuleDTO createDTO) {
        return capsuleService.createCapsule(createDTO, MOCK_USER_ID);
    }

    @GetMapping
    public Result<List<EmotionCapsuleVO>> listMyCapsules() {
        return capsuleService.listUserCapsules(MOCK_USER_ID);
    }

    @GetMapping("/{id}")
    public Result<EmotionCapsuleVO> getCapsule(@PathVariable("id") Long capsuleId) {
        return capsuleService.getCapsuleById(capsuleId, MOCK_USER_ID);
    }
} 