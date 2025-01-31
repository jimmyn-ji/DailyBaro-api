package com.project.hanfu.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PromotionInfoVO {
    @ApiModelProperty("活动id")
    private Long pid;

    @ApiModelProperty("活动名称")
    private String promotionName;

    @ApiModelProperty("活动类型")
    private String promotionType;

    @ApiModelProperty("活动详情")
    private String promotionDetail;

    @ApiModelProperty("活动图片")
    private String imgGuid;

    @ApiModelProperty("活动状态")
    private Integer state;
}
