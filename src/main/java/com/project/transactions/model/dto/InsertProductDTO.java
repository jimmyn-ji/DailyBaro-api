package com.project.transactions.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InsertProductDTO {
    //商品发布者
    private Long uid;
    //商品名称
    private String productName;
    //商品价格
    private BigDecimal price;
    //交易方式 0微信 1支付宝
    private Integer transactionMethod;
    //商品描述
    private String productDescription;
    //商品分类
    private Long ptId;
    //商品图片
    private String imageName;
    //图片路径
    private String imagePath;
    //商品状态 0上架 1下架
    private Integer status;

}
