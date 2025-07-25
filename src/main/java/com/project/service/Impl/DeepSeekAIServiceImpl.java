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
        // 优化智能问答prompt，要求结构化、分段、无markdown符号
        String prompt = "请以心理健康专家的身份，针对以下问题，给出科学、结构化、分段的解答。要求：1. 只用普通文本，不要使用markdown符号（如**、#、-等）、emoji或特殊排版；2. 内容分条、分段清晰，逻辑工整；3. 语言客观、简明。问题：" + question;
        return callDeepSeek(prompt);
    }

    @Override
    public Result<String> getResponseForDiary(String diaryContent) {
        // 优化prompt：即使内容较少也要尽量分析
        String prompt = "你是一个专业的情绪分析师，请根据以下日记内容，客观分析主要情绪及其可能原因，并给出简明建议。即使内容较少或表达模糊，也请基于已有信息尽量推测情绪类型和调节建议，不要只回复无法分析或让用户补充内容。输出要求：1. 先简要分析情绪及原因；2. 再给出具体建议；3. 不要使用markdown加粗、emoji或主观感叹词，直接分段输出。日记内容：" + diaryContent;
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