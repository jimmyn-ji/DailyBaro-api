package com.project.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

// OrderWithProduct.java (查询结果映射类)
@Data
public class OrderWithProduct {
    private Long orderId;
    private Long userId;
    private BigDecimal orderAmount;
    private String orderStatus;
    private Date createTime;
    private Date payTime;
    private Integer isPaid;
    private Integer isCancelled;

    // 商品信息
    private Long productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private String productImageUrl;
    private Integer productStock;
}
