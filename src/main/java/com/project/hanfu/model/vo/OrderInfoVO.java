package com.project.hanfu.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderInfoVO {
    @ApiModelProperty("订单id")
    private Long oid;

    @ApiModelProperty("订单guid")
    private String orderGuid;

    @ApiModelProperty("汉服名称")
    private String hanfuName;

    @ApiModelProperty("汉服数量")
    private BigDecimal hanfuQty;

    @ApiModelProperty("订单价格")
    private BigDecimal price;

    @ApiModelProperty("用户id")
    private Long uid;
}
