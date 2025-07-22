package com.project.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("orders")
public class Order {
    @Id
    private Long orderId;
    private Long userId;
    private BigDecimal orderAmount;
    private String orderStatus;
    private Date createTime;
    private Date payTime;
    private Integer isPaid;
    private Integer isCancelled;
}