package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InsertReviewDTO {

    @ApiModelProperty("用户id")
    private Long uid;

    @ApiModelProperty("订单id")
    private Long oid;

    @ApiModelProperty("是否发货 0未发货 1已发货")
    private Integer state;

    @ApiModelProperty("评价内容")
    private String review;

    @ApiModelProperty("是否评价 0未评价 1已评价")
    private Integer isreview;
}
