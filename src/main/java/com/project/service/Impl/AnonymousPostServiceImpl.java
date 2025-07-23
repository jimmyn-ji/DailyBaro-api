package com.project.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.mapper.AnonymousPostMapper;
import com.project.mapper.PostCommentMapper;
import com.project.mapper.PostLikeMapper;
import com.project.model.AnonymousPost;
import com.project.model.PostLike;
import com.project.model.dto.CreateAnonymousPostDTO;
import com.project.model.vo.AnonymousPostVO;
import com.project.service.AnonymousPostService;
import com.project.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AnonymousPostServiceImpl implements AnonymousPostService {

    @Autowired
    private AnonymousPostMapper anonymousPostMapper;

    @Autowired
    private PostLikeMapper postLikeMapper;

    @Autowired
    private PostCommentMapper postCommentMapper;

    @Override
    public Result<AnonymousPostVO> createPost(CreateAnonymousPostDTO createDTO, Long userId) {
        AnonymousPost post = new AnonymousPost();
        BeanUtils.copyProperties(createDTO, post);
        post.setUserId(userId);
        post.setCreateTime(new Date());

        anonymousPostMapper.insert(post);
        log.info("用户 {} 创建了一个新的匿名帖子，ID: {}", userId, post.getPostId());

        return Result.success(convertToVO(post));
    }

    @Override
    @Transactional
    public Result<Void> deletePost(Long postId, Long userId) {
        AnonymousPost post = anonymousPostMapper.selectById(postId);
        if (post == null) {
            return Result.fail("帖子不存在");
        }
        if (!post.getUserId().equals(userId)) {
            return Result.fail("无权删除此帖子");
        }

        // The database schema uses ON DELETE CASCADE,
        // so associated likes and comments will be deleted automatically.
        anonymousPostMapper.deleteById(postId);
        log.info("用户 {} 删除了帖子 {}", userId, postId);
        return Result.success();
    }

    @Override
    public Result<List<AnonymousPostVO>> getPublicPosts() {
        QueryWrapper<AnonymousPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("visibility", "public").orderByDesc("create_time");
        List<AnonymousPost> posts = anonymousPostMapper.selectList(queryWrapper);

        List<AnonymousPostVO> vos = posts.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return Result.success(vos);
    }

    @Override
    public Result<AnonymousPostVO> getPostById(Long postId) {
        AnonymousPost post = anonymousPostMapper.selectById(postId);
        if (post == null) {
            return Result.fail("帖子不存在");
        }
        return Result.success(convertToVO(post));
    }

    @Override
    public Result<Void> likePost(Long postId, Long userId) {
        // 检查帖子是否存在
        if (anonymousPostMapper.selectById(postId) == null) {
            return Result.fail("帖子不存在");
        }
        // 检查是否已点赞
        QueryWrapper<PostLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId).eq("user_id", userId);
        if (postLikeMapper.selectCount(queryWrapper) > 0) {
            return Result.success(); // 幂等性：重复点赞直接成功
        }

        PostLike like = new PostLike();
        like.setPostId(postId);
        like.setUserId(userId);
        postLikeMapper.insert(like);
        return Result.success();
    }

    @Override
    public Result<Void> unlikePost(Long postId, Long userId) {
        QueryWrapper<PostLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId).eq("user_id", userId);
        postLikeMapper.delete(queryWrapper);
        return Result.success();
    }

    private AnonymousPostVO convertToVO(AnonymousPost post) {
        AnonymousPostVO vo = new AnonymousPostVO();
        BeanUtils.copyProperties(post, vo);

        // 获取点赞数
        QueryWrapper<PostLike> likeQuery = new QueryWrapper<>();
        likeQuery.eq("post_id", post.getPostId());
        vo.setLikeCount(postLikeMapper.selectCount(likeQuery).intValue());

        // 获取评论数
        // Assuming PostCommentMapper is also available
        // QueryWrapper<PostComment> commentQuery = new QueryWrapper<>();
        // commentQuery.eq("post_id", post.getPostId());
        // vo.setCommentCount(postCommentMapper.selectCount(commentQuery).intValue());
        vo.setCommentCount(0); // Placeholder for now

        return vo;
    }
} 