package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class InsertOrderInfoDTO {
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

    @ApiModelProperty("订单状态 0未发货 1已发货")
    private Integer state;

    @ApiModelProperty("用户id")
    private Long uid;

    @ApiModelProperty("购物车id")
    private Long cid;

    @ApiModelProperty("汉服id")
    private Long hid;
}
