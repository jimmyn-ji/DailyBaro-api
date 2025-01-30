package com.project.hanfu.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReviewInfoVO {
    @ApiModelProperty("评论id")
    private Long rid;

    @ApiModelProperty("用户id")
    private Long uid;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("订单id")
    private Long oid;

    @ApiModelProperty("评论内容")
    private String content;
}
