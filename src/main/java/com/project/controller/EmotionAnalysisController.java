package com.project.controller;

import com.project.model.vo.EmotionDataPointVO;
import com.project.model.vo.EmotionShareVO;
import com.project.service.EmotionAnalysisService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/analysis")
public class EmotionAnalysisController {

    @Autowired
    private EmotionAnalysisService emotionAnalysisService;

    // Placeholder for the logged-in user ID
    private static final Long MOCK_USER_ID = 1L;

    @GetMapping("/fluctuation")
    public Result<List<EmotionDataPointVO>> getFluctuation(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return emotionAnalysisService.getEmotionFluctuation(MOCK_USER_ID, startDate, endDate);
    }

    @GetMapping("/distribution")
    public Result<List<EmotionShareVO>> getDistribution(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return emotionAnalysisService.getEmotionDistribution(MOCK_USER_ID, startDate, endDate);
    }
} 