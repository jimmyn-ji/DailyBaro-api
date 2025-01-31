package com.project.hanfu.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long pid;

    private Long ptid;

    private String promotionName;

    private String promotionDetail;

    private String imgGuid;

    private Date updateTime;

    private Date createTime;

    private Integer isdelete;
}
