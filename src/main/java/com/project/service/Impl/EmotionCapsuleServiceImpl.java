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

        capsuleMapper.insert(capsule);

        // Handle media files
        if (!CollectionUtils.isEmpty(createDTO.getMediaFiles())) {
            for (MultipartFile file : createDTO.getMediaFiles()) {
                Result<String> uploadResult = fileStorageService.storeFile(file);
                if (uploadResult.getCode() == 200) {
                    CapsuleMedia media = new CapsuleMedia();
                    media.setCapsuleId(capsule.getCapsuleId());
                    media.setMediaUrl(uploadResult.getData());
                    // Simplified media type determination
                    media.setMediaType(file.getContentType() != null && file.getContentType().startsWith("image") ? "image" : "other");
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