package com.project.transactions.model.dto;

import lombok.Data;

@Data
public class UploadProductImageDTO {
    //商品id
    private Long pid;
    //商品图片
    private String productImage;

}
