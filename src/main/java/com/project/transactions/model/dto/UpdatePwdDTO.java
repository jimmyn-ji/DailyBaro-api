package com.project.transactions.model.dto;

import lombok.Data;

@Data
public class UpdatePwdDTO {
    // 用户id
    private Long uid;
    // 旧密码
    private String oldPassword;
    // 新密码
    private String newPassword;
}
