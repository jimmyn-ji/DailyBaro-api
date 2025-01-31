package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InsertPromotionTypeDTO {
    @ApiModelProperty("活动类型")
    private String promotionType;
}
