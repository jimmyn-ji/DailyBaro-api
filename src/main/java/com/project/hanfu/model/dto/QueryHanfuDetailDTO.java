package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryHanfuDetailDTO {
    @ApiModelProperty("汉服主键id")
    private Long hid;
}
