package com.project.hanfu.model.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
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

    @ApiModelProperty("用户地址")
    private String address;

    @ApiModelProperty("用户电话")
    private String phoneNo;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("汉服id")
    private Long hid;

    @ApiModelProperty("订单状态 0未发货 1已发货")
    private Integer state;
}
