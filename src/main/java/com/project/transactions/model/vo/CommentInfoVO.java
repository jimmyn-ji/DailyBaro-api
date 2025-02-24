package com.project.transactions.model.vo;

import java.util.Date;

import lombok.Data;


@Data
public class CommentInfoVO {
    //评论id
    private Long cid;
    //评论内容
    private String content;
    //评论时间
    private Date createTime;
    //评论者
    private String commenter;
}
