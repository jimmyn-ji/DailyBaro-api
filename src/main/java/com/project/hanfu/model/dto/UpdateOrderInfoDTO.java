package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateOrderInfoDTO {
    @ApiModelProperty(value = "订单id")
    private Long oid;

    @ApiModelProperty(value = "订单状态 0未发货 1已发货")
    private Integer state;
}
