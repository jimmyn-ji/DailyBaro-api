package com.project.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.mapper.EmotionCapsuleMapper;
import com.project.mapper.UserMapper;
import com.project.model.EmotionCapsule;
import com.project.model.User;
import com.project.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class EmotionCapsuleReminderTask {

    @Autowired
    private EmotionCapsuleMapper capsuleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailService mailService;

    // 每天凌晨1点执行一次
    @Scheduled(cron = "0 0 1 * * ?")
    public void sendReminders() {
        Date now = new Date();
        QueryWrapper<EmotionCapsule> query = new QueryWrapper<>();
        query.le("open_time", now).eq("reminder_sent", 0);

        List<EmotionCapsule> dueCapsules = capsuleMapper.selectList(query);
        for (EmotionCapsule capsule : dueCapsules) {
            // 查找用户邮箱
            User user = userMapper.getMyInfo(capsule.getUserId());
            if (user != null) {
                if ("sms".equals(capsule.getReminderType())) {
                    // TODO: 调用短信API发送短信提醒
                    // smsService.sendSms(user.getPhone(), "您的情绪胶囊已到开启时间，请登录App查看！");
                } else if ("app_notification".equals(capsule.getReminderType())) {
                    // 应用内提醒只需设置reminder_sent=1，前端会弹窗
                }
                // 邮件提醒（可选）
                if (user.getEmail() != null) {
                    String to = user.getEmail();
                    String subject = "情绪胶囊开启提醒";
                    String content = "您的情绪胶囊已到开启时间，请登录App查看！";
                    mailService.sendSimpleMail(to, subject, content);
                }
            }
            capsule.setReminderSent(1);
            capsuleMapper.updateById(capsule);
        }
        log.info("情绪胶囊提醒任务执行完毕，提醒数量：{}", dueCapsules.size());
    }
} 