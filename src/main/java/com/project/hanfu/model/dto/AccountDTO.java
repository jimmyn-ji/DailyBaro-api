package com.project.hanfu.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountDTO {
    @ApiModelProperty(value = "用户账户")
    private String account;

}
