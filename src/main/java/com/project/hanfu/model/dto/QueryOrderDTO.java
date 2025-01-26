package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryOrderDTO {
    @ApiModelProperty(value = "页数")
    private int page;

    @ApiModelProperty(value = "查询条件")
    private String searchKey;

    @ApiModelProperty(value = "用户账号")
    private String account;

}
