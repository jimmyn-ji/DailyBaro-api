package com.project.hanfu.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserInfoVO {
    @ApiModelProperty(value = "用户账户",required = true)
    private String account;

    @ApiModelProperty(value = "用户id",required = true)
    private Long uid;

    @ApiModelProperty(value = "用户密码",required = true)
    private String password;

    @ApiModelProperty(value = "用户角色",required = true)
    private String role;

    @ApiModelProperty(value = "用户姓名",required = true)
    private String name;

    @ApiModelProperty(value = "联系方式",required = true)
    private String phoneNo;

    @ApiModelProperty(value = "收件地址",required = true)
    private String address;

    @ApiModelProperty(value = "用户积分")
    private BigDecimal points;

    @ApiModelProperty(value = "用户是否为vip")
    private Integer isVip;
}
