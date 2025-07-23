package com.project.controller;

import com.project.service.AIService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    @Qualifier("deepSeekAIService") // 使用DeepSeek AI
    private AIService aiService;

    @PostMapping("/query")
    public Result<String> askGeneralQuestion(@RequestBody AIQueryDTO query) {
        return aiService.getGeneralResponse(query.getQuestion());
    }

    @PostMapping("/diary-feedback")
    public Result<String> getDiaryFeedback(@RequestBody AIDiaryFeedbackDTO feedbackRequest) {
        return aiService.getResponseForDiary(feedbackRequest.getDiaryContent());
    }

    // Simple DTOs for request bodies
    static class AIQueryDTO {
        private String question;
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
    }

    static class AIDiaryFeedbackDTO {
        private String diaryContent;
        public String getDiaryContent() { return diaryContent; }
        public void setDiaryContent(String diaryContent) { this.diaryContent = diaryContent; }
    }
} 