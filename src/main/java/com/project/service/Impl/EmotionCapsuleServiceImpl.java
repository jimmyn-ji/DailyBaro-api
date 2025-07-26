package com.project.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.mapper.CapsuleMediaMapper;
import com.project.mapper.EmotionCapsuleMapper;
import com.project.model.CapsuleMedia;
import com.project.model.EmotionCapsule;
import com.project.model.dto.CreateCapsuleDTO;
import com.project.model.vo.EmotionCapsuleVO;
import com.project.model.vo.MediaVO;
import com.project.service.EmotionCapsuleService;
import com.project.service.FileStorageService;
import com.project.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmotionCapsuleServiceImpl implements EmotionCapsuleService {

    @Autowired
    private EmotionCapsuleMapper capsuleMapper;

    @Autowired
    private CapsuleMediaMapper mediaMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    @Transactional
    public Result<EmotionCapsuleVO> createCapsule(CreateCapsuleDTO createDTO, Long userId) {
        EmotionCapsule capsule = new EmotionCapsule();
        BeanUtils.copyProperties(createDTO, capsule);
        capsule.setUserId(userId);
        capsule.setCreateTime(new Date());
        capsule.setReminderType(createDTO.getReminderType()); // 保存提醒方式

        capsuleMapper.insert(capsule);

        // Handle media files
        if (!CollectionUtils.isEmpty(createDTO.getMediaFiles())) {
            for (MultipartFile file : createDTO.getMediaFiles()) {
                Result<String> uploadResult = fileStorageService.storeFile(file);
                if (uploadResult.getCode() == 200) {
                    CapsuleMedia media = new CapsuleMedia();
                    media.setCapsuleId(capsule.getCapsuleId());
                    media.setMediaUrl(uploadResult.getData());
                    
                    // 改进媒体类型识别
                    String contentType = file.getContentType();
                    String fileName = file.getOriginalFilename();
                    String mediaType = "other";
                    
                    if (contentType != null) {
                        if (contentType.startsWith("image/")) {
                            mediaType = "image";
                        } else if (contentType.startsWith("audio/")) {
                            mediaType = "audio";
                        } else if (contentType.startsWith("video/")) {
                            mediaType = "video";
                        }
                    }
                    // 文件名后缀优先判断
                    if (fileName != null) {
                        String lowerFileName = fileName.toLowerCase();
                        if (lowerFileName.endsWith(".jpg") || lowerFileName.endsWith(".jpeg") || 
                            lowerFileName.endsWith(".png") || lowerFileName.endsWith(".gif") ||
                            lowerFileName.endsWith(".webp") || lowerFileName.endsWith(".bmp")) {
                            mediaType = "image";
                        } else if (lowerFileName.endsWith(".mp3") || lowerFileName.endsWith(".wav") ||
                                   lowerFileName.endsWith(".ogg") || lowerFileName.endsWith(".aac") ||
                                   lowerFileName.endsWith(".m4a") || lowerFileName.endsWith(".ncm")) {
                            mediaType = "audio";
                        } else if (lowerFileName.endsWith(".mp4") || lowerFileName.endsWith(".avi") ||
                                   lowerFileName.endsWith(".mov") || lowerFileName.endsWith(".wmv") ||
                                   lowerFileName.endsWith(".flv")) {
                            mediaType = "video";
                        }
                    }
                    
                    media.setMediaType(mediaType);
                    mediaMapper.insert(media);
                } else {
                    log.error("Failed to upload file for capsule {}: {}", capsule.getCapsuleId(), uploadResult.getMessage());
                }
            }
        }
        return getCapsuleById(capsule.getCapsuleId(), userId);
    }

    @Override
    public Result<EmotionCapsuleVO> getCapsuleById(Long capsuleId, Long userId) {
        EmotionCapsule capsule = capsuleMapper.selectById(capsuleId);
        if (capsule == null || !capsule.getUserId().equals(userId)) {
            return Result.fail("情绪胶囊不存在或无权访问");
        }
        return Result.success(convertToVO(capsule));
    }

    @Override
    public Result<List<EmotionCapsuleVO>> listUserCapsules(Long userId) {
        QueryWrapper<EmotionCapsule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).orderByDesc("create_time");
        List<EmotionCapsule> capsules = capsuleMapper.selectList(queryWrapper);

        List<EmotionCapsuleVO> vos = capsules.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return Result.success(vos);
    }

    @Override
    public Result<List<EmotionCapsuleVO>> getUnreadReminders(Long userId) {
        Date now = new Date();
        QueryWrapper<EmotionCapsule> query = new QueryWrapper<>();
        query.eq("user_id", userId)
             .le("open_time", now)
             .eq("reminder_sent", 0);
        List<EmotionCapsule> dueCapsules = capsuleMapper.selectList(query);
        // 立即将这些胶囊的reminder_sent设为1
        for (EmotionCapsule capsule : dueCapsules) {
            capsule.setReminderSent(1);
            capsuleMapper.updateById(capsule);
        }
        List<EmotionCapsuleVO> voList = dueCapsules.stream().map(this::convertToVO).collect(Collectors.toList());
        return Result.success(voList);
    }

    @Override
    public Result<?> markReminderRead(Long capsuleId, Long userId) {
        EmotionCapsule capsule = capsuleMapper.selectById(capsuleId);
        if (capsule == null || !capsule.getUserId().equals(userId)) {
            return Result.fail("无权操作");
        }
        capsule.setReminderRead(1);
        capsuleMapper.updateById(capsule);
        return Result.success();
    }

    private EmotionCapsuleVO convertToVO(EmotionCapsule capsule) {
        EmotionCapsuleVO vo = new EmotionCapsuleVO();
        BeanUtils.copyProperties(capsule, vo);

        boolean isOpened = new Date().after(capsule.getOpenTime());
        vo.setOpened(isOpened);

        if (isOpened) {
            // Only populate content and media if the capsule is opened
            vo.setContent(capsule.getContent());
            QueryWrapper<CapsuleMedia> mediaQuery = new QueryWrapper<>();
            mediaQuery.eq("capsule_id", capsule.getCapsuleId());
            List<CapsuleMedia> mediaList = mediaMapper.selectList(mediaQuery);
            vo.setMedia(mediaList.stream().map(media -> {
                MediaVO mediaVO = new MediaVO();
                BeanUtils.copyProperties(media, mediaVO);
                return mediaVO;
            }).collect(Collectors.toList()));
        } else {
            // Explicitly set to null if not opened
            vo.setContent("这个胶囊还未到开启时间哦！");
            vo.setMedia(null);
        }

        return vo;
    }
} 