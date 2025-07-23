package com.project.controller;

import com.project.model.UserDrawnBox;
import com.project.service.MysteryBoxService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mystery-box")
public class MysteryBoxController {

    @Autowired
    private MysteryBoxService mysteryBoxService;

    // Placeholder for the logged-in user ID
    private static final Long MOCK_USER_ID = 1L;

    @PostMapping("/draw")
    public Result<UserDrawnBox> drawBox() {
        return mysteryBoxService.drawBox(MOCK_USER_ID);
    }

    @PostMapping("/complete/{id}")
    public Result<UserDrawnBox> completeTask(@PathVariable("id") Long drawnBoxId) {
        return mysteryBoxService.completeTask(drawnBoxId, MOCK_USER_ID);
    }
} 