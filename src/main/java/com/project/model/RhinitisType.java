package com.project.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@TableName("rhinitis_type")
public class RhinitisType {
    @Id
    private Long rid;  // 鼻炎信息表主键
    private String rhinitisType;  // 鼻炎种类
    private String symptom;  // 症状
    private String cause;  // 病因
    private String treatmentMethod;  // 治疗方法
    private String notice;  // 注意事项
    private String imageGuid;  // 图片
    private Integer isdelete;  // 是否删除 0已删除 1未删除
}