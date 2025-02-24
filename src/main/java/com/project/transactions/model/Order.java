package com.project.transactions.model;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long oid;
    private String orderNo;
    private Long uid;
    private Long pid;
    private BigDecimal price;
    private BigDecimal totalAmount;
    private Integer paymentMethod;
    private Integer status;
    private Integer isComment;
    private Date createTime;
    private Date updateTime;
    private Integer isdelete;
}
