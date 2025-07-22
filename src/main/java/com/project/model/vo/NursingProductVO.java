package com.project.model.vo;

import lombok.Data;

@Data
public class NursingProductVO {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private boolean isdeleted;
    private Integer stock;
}
