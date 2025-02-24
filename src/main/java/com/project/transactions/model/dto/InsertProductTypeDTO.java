package com.project.transactions.model.dto;

import lombok.Data;

@Data
public class InsertProductTypeDTO {
    //商品类别id
    private Long ptId;
    //商品类别名称
    private String productTypeName;
}
