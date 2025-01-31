package com.project.hanfu.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class QueryPage {

    @ApiModelProperty(value = "页码",required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小",required = true)
    private Integer pageSize;

}
