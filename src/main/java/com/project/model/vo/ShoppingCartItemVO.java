package com.project.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ShoppingCartItemVO {
    private Long itemId;
    private Long productId;
    private Integer quantity;
    private Long userId;
    private Date addTime;
    private String productName;
    private String productImageUrl;
    private double price;
    private Integer isdeleted;
}