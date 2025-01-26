package com.project.hanfu.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HanfuTypeVO {
    @ApiModelProperty("汉服类型id")
    private Long htid;

    @ApiModelProperty("汉服类型名称")
    private String hanfuType;
}
