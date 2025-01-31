package com.project.hanfu.model.dto;

import com.project.hanfu.model.base.QueryPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPromotionDTO extends QueryPage {
    @ApiModelProperty("活动名称")
    private String promotionName;
}
