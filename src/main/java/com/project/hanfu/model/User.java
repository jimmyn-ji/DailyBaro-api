package com.project.hanfu.model;

import lombok.Data;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long uid;

    private String account;

    private String name;

    private String password;

    private String phoneNo;

    private String address;

    private Long rid;

    private String role;

    private BigDecimal points;

    private Integer isvip;

    private Date updateTime;

    private Date createTime;

    private Integer isdelete;
}



