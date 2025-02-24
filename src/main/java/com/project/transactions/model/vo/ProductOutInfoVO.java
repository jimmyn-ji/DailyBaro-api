package com.project.transactions.model.vo;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProductOutInfoVO {
    //商品列表
    private List<ProductInfoVO> productInfoVOS;
    //商品分类ids
    private Set<String> productTypeNames;

}
