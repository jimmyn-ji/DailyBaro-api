package com.project.service;

import com.project.model.UserDrawnBox;
import com.project.util.Result;

public interface MysteryBoxService {

    /**
     * Draws a mystery box for the user for the current day.
     * @param userId The user's ID.
     * @return The drawn box item. Returns the already drawn box if tried again on the same day.
     */
    Result<UserDrawnBox> drawBox(Long userId);

    /**
     * Marks a task from a drawn box as completed.
     * @param drawnBoxId The ID of the drawn box record.
     * @param userId The user's ID (for authorization).
     * @return The updated drawn box record.
     */
    Result<UserDrawnBox> completeTask(Long drawnBoxId, Long userId);
} 