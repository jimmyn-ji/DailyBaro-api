package com.project.transactions.model.dto;

import lombok.Data;

@Data
public class UpdateUserInfoDTO {
    // 用户id
    private Long uid;
    // 手机号
    private String phone;
    // 邮箱
    private String email;
    // 状态 0:禁用 1:正常
    private Integer status;
}
