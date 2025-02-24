package com.project.transactions.model.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductInfoVO {
    //商品id
    private Long pid;
    //商品类别
    private Long ptId;
    //商品类别名称
    private String productTypeName;
    //商品名称
    private String productName;
    //商品图片
    private String productImage;
    //商品价格
    private BigDecimal price;
    //商品状态 0未上架 1上架
    private Integer status;
    //商品发布者
    private Long uid;
    private String account;
    //商品申请时间
    private Date updateTime;
    //商品描述
    private String productDescription;

}
