package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InsertUserDTO {
    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("用户昵称")
    private String name;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户手机号")
    private String phoneNo;

    @ApiModelProperty("用户地址")
    private String address;
}
