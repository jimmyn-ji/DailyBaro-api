package com.project.hanfu.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HanfuDetailVO {
    @ApiModelProperty("汉服主id")
    private Long hid;

    @ApiModelProperty("用户id")
    private Long uid;

    @ApiModelProperty("评价用户")
    private String userName;

    @ApiModelProperty("汉服种类id")
    private Long htid;

    @ApiModelProperty("汉服名称")
    private String hanfuName;

    @ApiModelProperty("汉服类型")
    private String hanfuType;

    @ApiModelProperty("汉服价格")
    private BigDecimal price;

    @ApiModelProperty("汉服详情")
    private String hanfuDetail;

    @ApiModelProperty("汉服图片")
    private String imgGuid;

    @ApiModelProperty("汉服状态 0下架 1上架")
    private Integer state;

    @ApiModelProperty("是否促销商品 0不是 1是")
    private Integer ispromotion;

    @ApiModelProperty("促销商品原价")
    private BigDecimal originalPrice;

    @ApiModelProperty("用户评价")
    private List<String> reviews;

}
