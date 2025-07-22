package com.project.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateOrderDTO {

    private Long userId;
    private Double orderAmount;
    private String orderStatus;
    private Date createTime;
    private Date payTime;
    private boolean isPaid;
    private boolean isCancelled;

    private List<Long> selectedItemIds;


}