package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateHanfuTypeDTO {
    @ApiModelProperty("汉服类型id")
    private Long htid;

    @ApiModelProperty("汉服类型名称")
    private String hanfuType;
}
