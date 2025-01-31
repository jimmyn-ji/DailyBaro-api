package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InsertPromotionDTO {
    @ApiModelProperty("促销活动名称")
    private String promotionName;

    @ApiModelProperty("促销活动类型")
    private String promotionType;

    @ApiModelProperty("促销活动详情")
    private String promotionDetail;

}
