package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateCommentsDTO {

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "留言id")
    private Long cmid;

    @ApiModelProperty(value = "回复评论")
    private String replyComments;

    @ApiModelProperty(value = "留言回复时间")
    private Date updateTime;
}
