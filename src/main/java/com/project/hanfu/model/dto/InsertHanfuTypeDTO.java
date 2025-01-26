package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InsertHanfuTypeDTO {
    @ApiModelProperty("汉服类型")
    private String hanfuType;

}
