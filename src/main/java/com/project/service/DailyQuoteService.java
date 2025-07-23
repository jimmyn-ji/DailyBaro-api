package com.project.service;

import com.project.model.DailyQuote;
import com.project.util.Result;

public interface DailyQuoteService {
    Result<DailyQuote> getRandomQuote();
} 