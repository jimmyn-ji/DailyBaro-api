package com.project.hanfu.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "promotion_type")
public class PromotionType {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long ptid;

    private String promotionType;

    private Date updateTime;

    private Date createTime;

    private Integer isdelete;
}
