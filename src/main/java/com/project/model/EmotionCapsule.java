package com.project.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("emotion_capsules")
public class EmotionCapsule {

    @TableId(value = "capsule_id", type = IdType.AUTO)
    private Long capsuleId;

    private Long userId;

    private String content;

    private Date openTime;

    private String reminderType;

    private Date createTime;

    private Integer reminderSent;
} 