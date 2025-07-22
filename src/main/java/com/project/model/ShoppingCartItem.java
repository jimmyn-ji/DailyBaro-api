package com.project.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@TableName("shopping_cart")
public class ShoppingCartItem {
    // 购物车项ID，自增主键
    @Id
    private Long itemId;
    // 商品ID
    private Long productId;
    // 商品数量
    private Integer quantity;
    // 用户ID
    private Long userId;
    // 添加时间，数据库默认值为当前时间戳
    private Date addTime;
    // 逻辑删除标志，0表示未删除，1表示已删除
    private Integer isdeleted;
    // 订单ID
    private Long orderId;
}