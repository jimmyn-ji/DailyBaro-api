package com.project.service.Impl;

import com.project.service.AIService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service("deepSeekAIService")
public class DeepSeekAIServiceImpl implements AIService {

    @Value("${deepseek.api-url}")
    private String apiUrl;

    @Value("${deepseek.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Result<String> getGeneralResponse(String question) {
        return callDeepSeek(question);
    }

    @Override
    public Result<String> getResponseForDiary(String diaryContent) {
        // 这里可以自定义prompt
        String prompt = "你是一个情绪分析师，请根据以下日记内容，分析情绪并给出建议：" + diaryContent;
        return callDeepSeek(prompt);
    }

    private Result<String> callDeepSeek(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "deepseek-chat");
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> systemMsg = new HashMap<>();
        systemMsg.put("role", "system");
        systemMsg.put("content", "你是一个情绪分析师。");
        messages.add(systemMsg);
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", prompt);
        messages.add(userMsg);
        body.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
            List choices = (List) response.getBody().get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map choice = (Map) choices.get(0);
                Map msg = (Map) choice.get("message");
                String content = (String) msg.get("content");
                return Result.success(content);
            }
            return Result.fail("AI无有效回复");
        } catch (Exception e) {
            return Result.fail("AI服务调用失败: " + e.getMessage());
        }
    }
} 