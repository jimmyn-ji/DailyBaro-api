package com.project.hanfu.model;

import lombok.Data;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "orders")
public class Orders {

    private int id;
    private String order_guid;
//    private String flower;
    private int amount;
//    private float price;
//    private float state;


    private Long oid;
    private String orderGuid;
    private String hanfuName;
    private BigDecimal hanfuQty;
    private BigDecimal price;
    private Long uid;
    private Integer state;
    private Date updateTime;
    private Date createTime;
    private Integer isdelete;

}



