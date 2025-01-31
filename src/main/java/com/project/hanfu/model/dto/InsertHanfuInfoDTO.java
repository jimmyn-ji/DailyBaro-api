package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InsertHanfuInfoDTO {

    @ApiModelProperty("汉服名称")
    private String hanfuName;

    @ApiModelProperty("汉服类型")
    private String hanfuType;

    @ApiModelProperty("汉服价格")
    private BigDecimal price;

    @ApiModelProperty("汉服详情")
    private String hanfuDetail;

}
