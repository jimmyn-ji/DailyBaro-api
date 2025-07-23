package com.project.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.mapper.MysteryBoxItemMapper;
import com.project.mapper.UserDrawnBoxMapper;
import com.project.model.MysteryBoxItem;
import com.project.model.UserDrawnBox;
import com.project.service.MysteryBoxService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class MysteryBoxServiceImpl implements MysteryBoxService {

    @Autowired
    private MysteryBoxItemMapper itemMapper;
    @Autowired
    private UserDrawnBoxMapper drawnBoxMapper;

    @Override
    public Result<UserDrawnBox> drawBox(Long userId) {
        // Check if the user has already drawn today
        LocalDate today = LocalDate.now();
        Date startOfDay = Date.from(today.atStartOfDay().toInstant(ZoneOffset.UTC));
        Date endOfDay = Date.from(today.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC));

        QueryWrapper<UserDrawnBox> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .between("draw_time", startOfDay, endOfDay);
        
        UserDrawnBox existingDraw = drawnBoxMapper.selectOne(queryWrapper);
        if (existingDraw != null) {
            return Result.success(existingDraw);
        }

        // Draw a new box
        List<MysteryBoxItem> allItems = itemMapper.selectList(null);
        if (allItems.isEmpty()) {
            return Result.fail("No mystery box items available.");
        }
        Random random = new Random();
        MysteryBoxItem drawnItem = allItems.get(random.nextInt(allItems.size()));

        UserDrawnBox newDraw = new UserDrawnBox();
        newDraw.setUserId(userId);
        newDraw.setBoxItemId(drawnItem.getBoxItemId());
        newDraw.setDrawTime(new Date());
        newDraw.setIsCompleted(false);
        drawnBoxMapper.insert(newDraw);

        return Result.success(newDraw);
    }

    @Override
    public Result<UserDrawnBox> completeTask(Long drawnBoxId, Long userId) {
        UserDrawnBox drawnBox = drawnBoxMapper.selectById(drawnBoxId);
        if (drawnBox == null || !drawnBox.getUserId().equals(userId)) {
            return Result.fail("Drawn box not found or you don't have permission.");
        }
        
        drawnBox.setIsCompleted(true);
        drawnBoxMapper.updateById(drawnBox);
        
        return Result.success(drawnBox);
    }
} 