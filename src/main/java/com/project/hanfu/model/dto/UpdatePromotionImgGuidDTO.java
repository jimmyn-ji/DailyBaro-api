package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePromotionImgGuidDTO {
    @ApiModelProperty("图片id")
    private String guid;

    @ApiModelProperty("汉服id")
    private Long pid;
}
