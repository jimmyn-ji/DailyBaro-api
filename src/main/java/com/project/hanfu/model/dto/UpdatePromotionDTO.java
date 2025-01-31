package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePromotionDTO{
    @ApiModelProperty("促销活动id")
    private Long pid;

    @ApiModelProperty("促销活动名称")
    private String promotionName;

    @ApiModelProperty("促销活动类型id")
    private Long ptid;

    @ApiModelProperty("促销活动类型")
    private String promotionType;

    @ApiModelProperty("促销活动详情")
    private String promotionDetail;

    @ApiModelProperty("促销活动图片")
    private String imgGuid;
}
