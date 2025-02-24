package com.project.transactions.model.dto;

import lombok.Data;

@Data
public class QueryInfoDTO {
    //查询内容
    private String queryContent;
    //用户id
    private Long uid;
}
