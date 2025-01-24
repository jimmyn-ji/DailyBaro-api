package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserPwDTO {
    @ApiModelProperty(value = "用户账户",required = true)
    private String account;

    @ApiModelProperty(value = "用户密码",required = true)
    private String password;

    @ApiModelProperty(value = "用户角色",required = true)
    private String role;
}
