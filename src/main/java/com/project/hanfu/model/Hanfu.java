package com.project.hanfu.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "hanfu")
public class Hanfu {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long hid;

    private String hanfuName;

    private String hanfuType;

    private BigDecimal price;

    private String hanfuDetail;

    private String imgGuid;

    private Integer state;

    private Date createTime;

    private Date updateTime;

    private Integer isdelete;

}
