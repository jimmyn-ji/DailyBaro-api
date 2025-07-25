package com.project.service;

import com.project.model.vo.EmotionDataPointVO;
import com.project.model.vo.EmotionShareVO;
import com.project.util.Result;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EmotionAnalysisService {

    /**
     * 获取指定时间范围内的情绪波动数据
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 情绪数据点列表
     */
    Result<List<EmotionDataPointVO>> getEmotionFluctuation(Long userId, Date startDate, Date endDate);
    Result<List<EmotionDataPointVO>> getEmotionFluctuation(Long userId, Date startDate, Date endDate, Long tagId);

    /**
     * 获取指定时间范围内各情绪的占比
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 情绪占比列表
     */
    Result<List<EmotionShareVO>> getEmotionDistribution(Long userId, Date startDate, Date endDate);
    Result<List<EmotionShareVO>> getEmotionDistribution(Long userId, Date startDate, Date endDate, Long tagId);

} 