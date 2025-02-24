package com.project.transactions.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class ProductDetailInfoVO {
    //商品id
    private Long pid;
    //商品名称
    private String productName;
    //商品价格
    private BigDecimal price;
    //商品图片
    private String productImage;
    //商品描述
    private String productDescription;
    //发布者id
    private Long uid;
    //商品发布者
    private String account;
    //商品发布时间
    private Date createTime;
    //商品类型
    private String productTypeName;
    //商品交易方式
    private Integer transactionMethod;
    //商品评论
    private List<CommentInfoVO> commentList;
}
