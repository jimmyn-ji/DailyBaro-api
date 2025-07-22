package com.project.model.vo;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductListVO {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer stock;
}