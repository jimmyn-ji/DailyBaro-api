package com.project.model.vo;

import com.project.model.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderOneVO {
    //用户ID
    private Long orderId;
    //订单金额
    private Long userId;
    //订单状态
    private BigDecimal orderAmount;
    //创建时间
    private String orderStatus;
    //支付时间
    private Date createTime;
    //是否已支付
    private Date payTime;
    //是否已取消
    private Boolean isPaid;
    //商品列表
    private Boolean isCancelled;

    private List<Product> products;
}
