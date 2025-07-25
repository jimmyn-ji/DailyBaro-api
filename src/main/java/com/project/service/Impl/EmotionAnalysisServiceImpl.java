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
        return getEmotionFluctuation(userId, startDate, endDate, null);
    }
    @Override
    public Result<List<EmotionDataPointVO>> getEmotionFluctuation(Long userId, Date startDate, Date endDate, Long tagId) {
        // 只根据userId和时间区间统计所有日记的情绪波动，不再处理tagId
        List<Diary> diaries = diaryMapper.selectList(null).stream()
            .filter(d -> d.getUserId().equals(userId)
                    && !d.getCreateTime().before(startDate)
                    && !d.getCreateTime().after(endDate))
            .collect(Collectors.toList());
        List<EmotionDataPointVO> result = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        while (!cal.getTime().after(endDate)) {
            Date dayStart = getDayStart(cal.getTime());
            Date dayEnd = getDayEnd(cal.getTime());
            // 筛选当天的日记
            List<Diary> dayDiaries = diaries.stream()
                .filter(d -> !d.getCreateTime().before(dayStart) && !d.getCreateTime().after(dayEnd))
                .collect(Collectors.toList());
            List<Double> emotionValues = new ArrayList<>();
            for (Diary diary : dayDiaries) {
                List<String> tags = diaryMapper.findTagsByDiaryId(diary.getDiaryId());
                for (String tag : tags) {
                    if (EMOTION_MAP.containsKey(tag)) {
                        emotionValues.add(EMOTION_MAP.get(tag));
                    }
                }
            }
            Double avg = null;
            if (!emotionValues.isEmpty()) {
                avg = emotionValues.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            }
            EmotionDataPointVO vo = new EmotionDataPointVO();
            vo.setDate(new java.sql.Date(dayStart.getTime()));
            vo.setValue(avg);
            result.add(vo);
            cal.add(Calendar.DATE, 1);
        }
        return Result.success(result);
    }

    // 工具方法：获取一天的起始和结束时间
    private Date getDayStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    private Date getDayEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    @Override
    public Result<List<EmotionShareVO>> getEmotionDistribution(Long userId, Date startDate, Date endDate) {
        return getEmotionDistribution(userId, startDate, endDate, null);
    }
    @Override
    public Result<List<EmotionShareVO>> getEmotionDistribution(Long userId, Date startDate, Date endDate, Long tagId) {
        List<String> allTags;
        if (tagId != null) {
            List<Long> diaryIds = diaryTagMapper.findDiaryIdsByTagIds(Collections.singletonList(tagId));
            if (diaryIds.isEmpty()) {
                return Result.success(Collections.emptyList());
            }
            allTags = new ArrayList<>();
            for (Long diaryId : diaryIds) {
                allTags.addAll(diaryMapper.findTagsByDiaryId(diaryId));
            }
        } else {
            allTags = diaryMapper.findTagsByUserIdAndDateRange(userId, startDate, endDate);
        }
        if (allTags.isEmpty()) {
            return Result.success(Collections.emptyList());
        }
        Map<String, Long> emotionCounts = allTags.stream()
                .filter(EMOTION_MAP::containsKey)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        long totalEmotions = emotionCounts.values().stream().mapToLong(Long::longValue).sum();
        if (totalEmotions == 0) {
            return Result.success(Collections.emptyList());
        }
        List<EmotionShareVO> result = emotionCounts.entrySet().stream()
                .map(entry -> new EmotionShareVO(entry.getKey(), (double) entry.getValue() / totalEmotions * 100))
                .sorted(Comparator.comparing(EmotionShareVO::getPercentage).reversed())
                .collect(Collectors.toList());
        return Result.success(result);
    }
} 