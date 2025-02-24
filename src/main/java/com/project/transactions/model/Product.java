package com.project.transactions.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "product")

public class Product {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long pid;
    //商品名称
    private String productName;
    //商品描述
    private String productDescription;
    //商品价格
    private BigDecimal price;
    //商品状态 0上架 1下架
    private Integer status;
    //商品分类
    private Long ptId;
    //商品发布者
    private Long uid;
    //商品图片
    private String imageName;
    //商品图片路径
    private String imagePath;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //是否删除 0未删除 1已删除
    private Integer isdelete;
}
