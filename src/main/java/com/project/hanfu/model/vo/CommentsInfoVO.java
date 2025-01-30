package com.project.hanfu.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CommentsInfoVO {
    @ApiModelProperty("留言人")
    private String userName;

    @ApiModelProperty("留言id")
    private Long cmid;

    @ApiModelProperty("留言内容")
    private String comments;

    @ApiModelProperty(value = "回复评论")
    private String replyComments;

    @ApiModelProperty("是否回复 0未回复 1已回复")
    private Integer isreply;

    @ApiModelProperty("留言时间")
    private Date createTime;

    @ApiModelProperty("回复时间")
    private Date updateTime;
}
