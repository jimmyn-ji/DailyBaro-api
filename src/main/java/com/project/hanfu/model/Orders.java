package com.project.hanfu.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long oid;

    private Long uid;

    private Long hid;

    private Long cid;

    private String orderGuid;

    private String hanfuName;

    private BigDecimal hanfuQty;

    private BigDecimal price;

    private Integer state;

    private String review;

    private Integer isreview;

    private Date updateTime;

    private Date createTime;

    private Integer isdelete;
}



