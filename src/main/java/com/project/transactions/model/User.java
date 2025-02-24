package com.project.transactions.model;

import lombok.Data;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long uid;

    private String account;

    private String password;

    private String phone;

    private String email;

    private Integer role;

    private Integer status;

    private String address;

    private Date updateTime;

    private Date createTime;

    private Integer isdelete;
}



