package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteOrderDTO {
    @ApiModelProperty("订单id")
    private Long oid;
}
