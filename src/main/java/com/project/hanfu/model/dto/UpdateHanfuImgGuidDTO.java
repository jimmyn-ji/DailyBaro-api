package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateHanfuImgGuidDTO {
    @ApiModelProperty("图片id")
    private String guid;

    @ApiModelProperty("汉服id")
    private Long hid;
}
