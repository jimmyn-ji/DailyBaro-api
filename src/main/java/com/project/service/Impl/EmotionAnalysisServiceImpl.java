package com.project.service.Impl;

import com.project.mapper.DiaryMapper;
import com.project.mapper.DiaryTagMapper;
import com.project.mapper.TagMapper;
import com.project.model.Diary;
import com.project.model.DiaryTag;
import com.project.model.Tag;
import com.project.model.vo.EmotionDataPointVO;
import com.project.model.vo.EmotionShareVO;
import com.project.service.EmotionAnalysisService;
import com.project.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmotionAnalysisServiceImpl implements EmotionAnalysisService {

    @Autowired
    private DiaryMapper diaryMapper;
    @Autowired
    private DiaryTagMapper diaryTagMapper;
    @Autowired
    private TagMapper tagMapper;

    private static final Map<String, Double> EMOTION_MAP = new HashMap<>();

    static {
        EMOTION_MAP.put("开心", 1.0);
        EMOTION_MAP.put("激动", 0.8);
        EMOTION_MAP.put("平静", 0.5);
        EMOTION_MAP.put("无聊", 0.0);
        EMOTION_MAP.put("疲惫", -0.4);
        EMOTION_MAP.put("难过", -0.8);
        EMOTION_MAP.put("焦虑", -0.9);
        EMOTION_MAP.put("愤怒", -1.0);
    }

    @Override
    public Result<List<EmotionDataPointVO>> getEmotionFluctuation(Long userId, Date startDate, Date endDate) {
        // This is a simplified implementation. A real-world scenario would require more complex queries.
        // For now, we fetch all diaries and process in memory.
        List<Diary> diaries = diaryMapper.selectList(null); // Simplified
        // ... logic to calculate daily average emotion value ...
        return Result.success(new ArrayList<>());
    }

    @Override
    public Result<List<EmotionShareVO>> getEmotionDistribution(Long userId, Date startDate, Date endDate) {
        // Use the custom, efficient query to get all relevant tags.
        List<String> allTags = diaryMapper.findTagsByUserIdAndDateRange(userId, startDate, endDate);

        if (allTags.isEmpty()) {
            return Result.success(Collections.emptyList());
        }

        // Filter for emotion tags and count them in memory
        Map<String, Long> emotionCounts = allTags.stream()
                .filter(EMOTION_MAP::containsKey)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long totalEmotions = emotionCounts.values().stream().mapToLong(Long::longValue).sum();
        if (totalEmotions == 0) {
            return Result.success(Collections.emptyList());
        }

        // Calculate percentage
        List<EmotionShareVO> result = emotionCounts.entrySet().stream()
                .map(entry -> new EmotionShareVO(entry.getKey(), (double) entry.getValue() / totalEmotions * 100))
                .sorted(Comparator.comparing(EmotionShareVO::getPercentage).reversed()) // Sort by percentage desc
                .collect(Collectors.toList());

        return Result.success(result);
    }
} 