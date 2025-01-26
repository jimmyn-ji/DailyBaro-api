package com.project.hanfu.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "hanfu_type")
public class HanfuType {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long htid;

    private String hanfuType;

    private Date updateTime;

    private Date createTime;

    private Integer isdelete;
}
