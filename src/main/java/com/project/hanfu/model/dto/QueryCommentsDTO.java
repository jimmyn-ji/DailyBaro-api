package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QueryCommentsDTO {
    @ApiModelProperty(value = "查询条件")
    private String searchKey;
}
