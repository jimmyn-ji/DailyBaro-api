package com.project.controller;

import com.project.model.DailyQuote;
import com.project.service.DailyQuoteService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quotes")
public class DailyQuoteController {

    @Autowired
    private DailyQuoteService dailyQuoteService;

    @GetMapping("/random")
    public Result<DailyQuote> getRandomQuote() {
        return dailyQuoteService.getRandomQuote();
    }
} 