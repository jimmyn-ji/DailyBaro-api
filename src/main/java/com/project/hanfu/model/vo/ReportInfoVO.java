package com.project.hanfu.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReportInfoVO {

    @ApiModelProperty("日销售额")
    private BigDecimal dailySales;

    @ApiModelProperty("周销售额")
    private BigDecimal weeklySales;

    @ApiModelProperty("月销售额")
    private BigDecimal monthlySales;
}
