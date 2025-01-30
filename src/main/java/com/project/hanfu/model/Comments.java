package com.project.hanfu.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(generator="JDBC")
    private Long cmid;

    private Long uid;

    private String comments;

    private String replyComments;

    private Integer isreply;

    private Date updateTime;

    private Date createTime;

    private Integer isdelete;

}
