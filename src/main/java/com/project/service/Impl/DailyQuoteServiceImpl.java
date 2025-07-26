package com.project.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.mapper.DailyQuoteMapper;
import com.project.model.DailyQuote;
import com.project.service.DailyQuoteService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class DailyQuoteServiceImpl implements DailyQuoteService {

    @Autowired
    private DailyQuoteMapper dailyQuoteMapper;

    @Override
    public Result<DailyQuote> getRandomQuote() {
        // A more efficient way for large tables would be to get a random ID within the range
        // or use a native SQL query with RAND() or a similar function.
        // For a small number of quotes, this is acceptable.
        List<DailyQuote> allQuotes = dailyQuoteMapper.selectList(new QueryWrapper<>());
        if (allQuotes.isEmpty()) {
            return Result.fail("No quotes available.");
        }
        Random random = new Random();
        DailyQuote randomQuote = allQuotes.get(random.nextInt(allQuotes.size()));
        return Result.success(randomQuote);
    }
    
    @Override
    public Result<DailyQuote> getRandomQuoteForUser(Long userId) {
        List<DailyQuote> allQuotes = dailyQuoteMapper.selectList(new QueryWrapper<>());
        if (allQuotes.isEmpty()) {
            return Result.fail("No quotes available.");
        }
        
        // 使用用户ID和当前日期作为随机种子，确保同一天同一用户看到相同的随机日签
        LocalDate today = LocalDate.now();
        long seed = userId * 10000 + today.getYear() * 10000 + today.getMonthValue() * 100 + today.getDayOfMonth();
        Random random = new Random(seed);
        
        DailyQuote randomQuote = allQuotes.get(random.nextInt(allQuotes.size()));
        return Result.success(randomQuote);
    }
} 