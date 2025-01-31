package com.project.hanfu.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PromotionTypeInfoVO {

    @ApiModelProperty("活动类型")
    private Long ptid;

    @ApiModelProperty("活动类型")
    private String promotionType;
}
