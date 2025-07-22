package com.project.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderVO {
    private Long orderId;
    private Long userId;
    private Double orderAmount;
    private String orderStatus;
    private Date createTime;
    private Date payTime;
    private Integer isPaid;
    private Integer isCancelled;
}
