package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeletePromotionDTO {
    @ApiModelProperty("促销活动id")
    private Long pid;
}
