package com.project.hanfu.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDTO {
    private String account; // 用户账号
    private Double totalAmount; // 总金额
    private List<InsertOrderInfoDTO> items; // 购物车商品列表
}