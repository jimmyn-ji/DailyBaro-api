package com.project.controller;

import com.project.model.dto.CreateAnonymousPostDTO;
import com.project.model.vo.AnonymousPostVO;
import com.project.service.AnonymousPostService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anonymous-posts")
public class AnonymousPostController {

    @Autowired
    private AnonymousPostService anonymousPostService;

    // Placeholder for the logged-in user ID
    private static final Long MOCK_USER_ID = 2L;

    @PostMapping
    public Result<AnonymousPostVO> createPost(@RequestBody CreateAnonymousPostDTO createDTO) {
        return anonymousPostService.createPost(createDTO, MOCK_USER_ID);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deletePost(@PathVariable("id") Long postId) {
        return anonymousPostService.deletePost(postId, MOCK_USER_ID);
    }

    @GetMapping
    public Result<List<AnonymousPostVO>> getPublicPosts() {
        return anonymousPostService.getPublicPosts();
    }

    @GetMapping("/{id}")
    public Result<AnonymousPostVO> getPostById(@PathVariable("id") Long postId) {
        return anonymousPostService.getPostById(postId);
    }

    @PostMapping("/{id}/like")
    public Result<Void> likePost(@PathVariable("id") Long postId) {
        return anonymousPostService.likePost(postId, MOCK_USER_ID);
    }

    @DeleteMapping("/{id}/like")
    public Result<Void> unlikePost(@PathVariable("id") Long postId) {
        return anonymousPostService.unlikePost(postId, MOCK_USER_ID);
    }
} 