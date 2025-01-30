package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class InsertCommentsDTO {

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "留言id")
    private Long cmid;

    @ApiModelProperty(value = "留言内容")
    private String comments;

    @ApiModelProperty(value = "留言创建时间")
    private Date createTime;
}
