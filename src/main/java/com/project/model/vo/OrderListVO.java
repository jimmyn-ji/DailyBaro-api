package com.project.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderListVO {
    private Long orderId;
    private Long userId;
    private BigDecimal orderAmount;
    private String orderStatus;
    private Date createTime;
    private Date payTime;
    private Integer paid;
    private Integer cancelled;
    private List<ProductListVO> products;


}


