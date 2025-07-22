package com.project.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@TableName("comments")
public class Comments {
    @Id
    private Long commentId;
    private Long postId;
    private Long userId;
    private String content;
    private Date createTime;
    private Date updateTime;

    private Integer isReply;      // 是否为回复(0:否,1:是)
    private Long replyToCommentId; // 被回复评论ID
    private Long replyToUserId;    // 被回复用户ID
    private Integer replyCount;    // 回复数
}