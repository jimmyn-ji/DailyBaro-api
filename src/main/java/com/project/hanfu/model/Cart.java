package com.project.hanfu.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@Table(name = "cart")
public class Cart {

    private int id;
    private int fid;
//    private String flower;
//    private int amount;
//    private String account;



        private Long cid;

        private Long hid;

        private String hanfuName;

        private BigDecimal hanfuQty;

        private BigDecimal price;

        private Long uid;

        private Date updateTime;

        private Date createTime;

        private Integer isdelete;

}
//@Data
//@Table(name = "cart")
//public class Cart {
//    @Id
//    @GeneratedValue(generator = "JDBC")
//    private Long cid;
//
//    private Long hid;
//
//    private String hanfuName;
//
//    private BigDecimal hanfuQty;
//
//    private BigDecimal price;
//
//    private Long uid;
//
//    private Date updateTime;
//
//    private Date createTime;
//
//    private Integer isdelete;
//}

