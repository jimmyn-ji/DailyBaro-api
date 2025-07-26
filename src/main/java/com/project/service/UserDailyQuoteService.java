package com.project.service;

import com.project.model.UserDailyQuote;
import com.project.util.Result;

public interface UserDailyQuoteService {
    Result<UserDailyQuote> getUserQuote(Long userId);
    Result<UserDailyQuote> saveOrUpdateUserQuote(Long userId, String content);
} 