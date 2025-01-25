package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CidDTO {
    @ApiModelProperty(value = "购物车主键id")
    private Long cid;
}
