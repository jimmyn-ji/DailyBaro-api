package com.project.transactions.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table; 

import lombok.Data;

@Data
@Table(name = "product_type")
public class ProductType {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long ptId;
    //商品分类名称
    private String productTypeName;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //是否删除 0未删除 1已删除
    private Integer isdelete;
}
