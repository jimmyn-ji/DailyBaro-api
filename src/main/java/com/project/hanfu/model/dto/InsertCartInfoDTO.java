package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InsertCartInfoDTO {
    @ApiModelProperty("购物车id")
    private Long cid;

    @ApiModelProperty("汉服id")
    private Long hid;

    @ApiModelProperty("汉服名称")
    private String hanfuName;

    @ApiModelProperty("汉服数量")
    private BigDecimal hanfuQty;

    @ApiModelProperty("汉服价格")
    private BigDecimal price;

    @ApiModelProperty("用户id")
    private Long uid;

    private String account;
}
