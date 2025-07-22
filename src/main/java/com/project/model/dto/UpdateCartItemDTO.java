package com.project.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateCartItemDTO {
    private Long itemId;
    private Long productId;
    private Integer quantity;
    private Long userId;
    private Date addTime;
    private Double price; // 添加价格字段
}