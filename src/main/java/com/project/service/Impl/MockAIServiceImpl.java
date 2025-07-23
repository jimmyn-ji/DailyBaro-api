package com.project.service.Impl;

import com.project.service.AIService;
import com.project.util.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("mockAIService") // Give it a specific name to distinguish from a real implementation later
public class MockAIServiceImpl implements AIService {

    private static final Map<String, String> DIARY_KEYWORD_RESPONSES = new HashMap<>();
    private static final Map<String, String> GENERAL_QUESTION_RESPONSES = new HashMap<>();

    static {
        // Responses for diary content keywords
        DIARY_KEYWORD_RESPONSES.put("失眠", "检测到您提到了失眠，尝试一些放松技巧可能会有帮助，比如深呼吸、冥想或听一些舒缓的音乐。");
        DIARY_KEYWORD_RESPONSES.put("压力", "感到有压力时，记得给自己一些喘息的时间。散步、与朋友聊天或做一些你喜欢的事情都能有效缓解压力。");
        DIARY_KEYWORD_RESPONSES.put("焦虑", "焦虑是很常见的情绪。试着将注意力集中在当下，或者写下你的担忧，这有助于理清思绪，减轻焦虑感。");

        // Responses for general questions
        GENERAL_QUESTION_RESPONSES.put("如何缓解压力", "缓解压力的好方法有很多，例如：进行适度的体育锻炼，如散步、瑜伽或跑步；保证充足的睡眠；与朋友或家人倾诉；或者培养一个能让你放松的爱好。");
        GENERAL_QUESTION_RESPONSES.put("我为什么会感到焦虑", "焦虑可能由多种因素引起，包括工作压力、人际关系、健康问题或对未来的不确定性。了解触发你焦虑的原因是管理它的第一步。");
    }

    @Override
    public Result<String> getGeneralResponse(String question) {
        for (Map.Entry<String, String> entry : GENERAL_QUESTION_RESPONSES.entrySet()) {
            if (question.contains(entry.getKey())) {
                return Result.success(entry.getValue());
            }
        }
        return Result.success("这是一个很好的问题，我现在还在学习中，暂时无法回答。");
    }

    @Override
    public Result<String> getResponseForDiary(String diaryContent) {
        for (Map.Entry<String, String> entry : DIARY_KEYWORD_RESPONSES.entrySet()) {
            if (diaryContent.contains(entry.getKey())) {
                return Result.success(entry.getValue());
            }
        }
        // If no keywords are found, we can return a generic supportive message
        return Result.success("感谢你的记录，很高兴能成为你的倾听者。");
    }
} 