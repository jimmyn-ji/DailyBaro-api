package com.project.controller;

import com.project.model.DailyQuote;
import com.project.model.UserDailyQuote;
import com.project.service.DailyQuoteService;
import com.project.service.UserDailyQuoteService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/quotes")
public class DailyQuoteController {

    @Autowired
    private DailyQuoteService dailyQuoteService;

    @Autowired
    private UserDailyQuoteService userDailyQuoteService;

    @GetMapping("/random")
    public Result<DailyQuote> getRandomQuote() {
        return dailyQuoteService.getRandomQuote();
    }
    
    @GetMapping("/random/user")
    public Result<DailyQuote> getRandomQuoteForUser(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        if (userId == null) return Result.fail("用户未登录");
        return dailyQuoteService.getRandomQuoteForUser(userId);
    }

    @GetMapping("/custom")
    public Result<UserDailyQuote> getCustomQuote(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        if (userId == null) return Result.fail("用户未登录");
        return userDailyQuoteService.getUserQuote(userId);
    }

    @PostMapping("/custom")
    public Result<UserDailyQuote> saveCustomQuote(@RequestBody UserDailyQuote quote, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        if (userId == null) return Result.fail("用户未登录");
        return userDailyQuoteService.saveOrUpdateUserQuote(userId, quote.getContent());
    }

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String uidStr = request.getHeader("uid");
        if (uidStr == null) return null;
        try {
            return Long.parseLong(uidStr);
        } catch (Exception e) {
            return null;
        }
    }
} 