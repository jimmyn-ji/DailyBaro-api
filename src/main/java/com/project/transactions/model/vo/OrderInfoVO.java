package com.project.transactions.model.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderInfoVO {
    private Long oid;
    private String orderNo;
    private Long pid;
    //商品名称
    private String productName;
    //商品价格
    private BigDecimal price;
    //商品分类
    private Long ptId;
    //分类名称
    private String productTypeName;
    //商品发布者
    private Long uid;
    // private BigDecimal totalAmount;
    //支付方式
    private String paymentMethod;
    //订单状态
    private Integer status;
    //收货地址
    private String address;
}
