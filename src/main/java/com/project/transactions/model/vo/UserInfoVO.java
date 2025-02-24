package com.project.transactions.model.vo;

import lombok.Data;

@Data
public class UserInfoVO {
    private Long uid;
    private String account;
    private String phone;
    private String email;
    private Integer role;// 0-普通用户 1-管理员
    private Integer status;// 0-正常 1-禁用
}