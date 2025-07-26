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

    private Date openTime;
    private Date createTime;
    private Long userId;
    private String content;

    private String reminderType; // 提醒方式 app_notification/sms

    private Integer reminderSent; // 0未提醒 1已提醒

    private Integer reminderRead; // 0未读 1已读
} 