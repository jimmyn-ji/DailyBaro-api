package com.project.hanfu.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ReportByHanfuTypeInfoVO {

    @ApiModelProperty("汉服类型名称")
    private Map<Long, BigDecimal> salesByTypesMap;
}
