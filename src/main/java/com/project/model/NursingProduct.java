package com.project.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@TableName("nursing_product")
public class NursingProduct {
    @Id
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private boolean isdeleted;
    private Integer stock;

}
