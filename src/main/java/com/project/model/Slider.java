package com.project.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@TableName("slider")
public class Slider {
    @Id
    private Long sliderId;
    private String title;
    private String imageUrl;
    private Integer isdeleted;
}