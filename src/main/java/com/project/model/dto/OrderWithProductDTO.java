package com.project.model.dto;

import com.project.model.vo.ProductVO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderWithProductDTO {
    private Long orderId;
    private Long userId;
    private BigDecimal orderAmount;
    private String orderStatus;
    private LocalDateTime createTime;
    private LocalDateTime payTime;
    private Boolean isPaid;
    private Boolean isCancelled;

    private ProductVO product;
}
