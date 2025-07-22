package com.project.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AddToCartDTO {
    private Long productId;
    private Integer quantity;
    private Long userId;
    private Date addTime;
}
