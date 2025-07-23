package com.project.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.mapper.DiaryMapper;
import com.project.mapper.DiaryMediaMapper;
import com.project.mapper.DiaryTagMapper;
import com.project.mapper.TagMapper;
import com.project.model.Diary;
import com.project.model.DiaryMedia;
import com.project.model.DiaryTag;
import com.project.model.Tag;
import com.project.model.dto.CreateDiaryDTO;
import com.project.model.dto.QueryDiaryDTO;
import com.project.model.dto.UpdateDiaryDTO;
import com.project.model.vo.DiaryVO;
import com.project.model.vo.MediaVO;
import com.project.service.DiaryService;
import com.project.service.FileStorageService;
import com.project.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DiaryServiceImpl implements DiaryService {

    @Autowired
    private DiaryMapper diaryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private DiaryTagMapper diaryTagMapper;

    @Autowired
    private DiaryMediaMapper diaryMediaMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    @Transactional
    public Result<DiaryVO> createDiary(CreateDiaryDTO createDiaryDTO, Long userId) {
        // 1. Convert DTO to Diary entity and save it
        Diary diary = new Diary();
        diary.setUserId(userId);
        diary.setTitle(createDiaryDTO.getTitle());
        diary.setContent(createDiaryDTO.getContent());
        diary.setStatus("published"); // Default status
        diary.setCreateTime(new Date());
        diary.setUpdateTime(new Date());
        diaryMapper.insert(diary);

        // 2. Handle tags
        handleTags(createDiaryDTO.getTags(), diary.getDiaryId(), userId);

        // 3. Handle media files
        if (!CollectionUtils.isEmpty(createDiaryDTO.getMediaFiles())) {
            for (MultipartFile file : createDiaryDTO.getMediaFiles()) {
                Result<String> uploadResult = fileStorageService.storeFile(file);
                if (uploadResult.getCode() == 200) {
                    DiaryMedia media = new DiaryMedia();
                    media.setDiaryId(diary.getDiaryId());
                    media.setMediaUrl(uploadResult.getData());
                    media.setMediaType(determineMediaType(file.getContentType()));
                    diaryMediaMapper.insert(media);
                } else {
                    // In a real app, you might want to roll back the transaction
                    log.error("Failed to upload file for diary {}: {}", diary.getDiaryId(), uploadResult.getMessage());
                }
            }
        }

        // 4. Convert final entity to VO and return
        return getDiaryById(diary.getDiaryId());
    }

    private void handleTags(List<String> tagNames, Long diaryId, Long userId) {
        if (CollectionUtils.isEmpty(tagNames)) {
            return;
        }
        for (String tagName : tagNames) {
            // Find if tag exists for the user
            QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId).eq("tag_name", tagName);
            Tag tag = tagMapper.selectOne(queryWrapper);

            // If not, create it
            if (tag == null) {
                tag = new Tag();
                tag.setUserId(userId);
                tag.setTagName(tagName);
                tagMapper.insert(tag);
            }

            // Associate tag with diary
            DiaryTag diaryTag = new DiaryTag();
            diaryTag.setDiaryId(diaryId);
            diaryTag.setTagId(tag.getTagId());
            diaryTagMapper.insert(diaryTag);
        }
    }

    private String determineMediaType(String contentType) {
        if (contentType == null) {
            return "unknown";
        }
        if (contentType.startsWith("image/")) {
            return "image";
        } else if (contentType.startsWith("video/")) {
            return "video";
        } else if (contentType.startsWith("audio/")) {
            return "audio";
        }
        return "unknown";
    }

    @Override
    @Transactional
    public Result<DiaryVO> updateDiary(UpdateDiaryDTO updateDiaryDTO) {
        Diary diary = diaryMapper.selectById(updateDiaryDTO.getDiaryId());
        if (diary == null) {
            return Result.fail("日记不存在");
        }
        // 1. 更新基本字段
        if (updateDiaryDTO.getTitle() != null) diary.setTitle(updateDiaryDTO.getTitle());
        if (updateDiaryDTO.getContent() != null) diary.setContent(updateDiaryDTO.getContent());
        diary.setUpdateTime(new Date());
        diaryMapper.updateById(diary);

        // 2. 处理标签
        QueryWrapper<DiaryTag> tagDelQuery = new QueryWrapper<>();
        tagDelQuery.eq("diary_id", diary.getDiaryId());
        diaryTagMapper.delete(tagDelQuery);
        handleTags(updateDiaryDTO.getTags(), diary.getDiaryId(), diary.getUserId());

        // 3. 处理媒体
        if (!CollectionUtils.isEmpty(updateDiaryDTO.getMediaIdsToDelete())) {
            for (Long mediaId : updateDiaryDTO.getMediaIdsToDelete()) {
                DiaryMedia media = diaryMediaMapper.selectById(mediaId);
                if (media != null) {
                    deletePhysicalFile(media.getMediaUrl());
                    diaryMediaMapper.deleteById(mediaId);
                }
            }
        }
        if (!CollectionUtils.isEmpty(updateDiaryDTO.getNewMediaFiles())) {
            for (MultipartFile file : updateDiaryDTO.getNewMediaFiles()) {
                Result<String> uploadResult = fileStorageService.storeFile(file);
                if (uploadResult.getCode() == 200) {
                    DiaryMedia media = new DiaryMedia();
                    media.setDiaryId(diary.getDiaryId());
                    media.setMediaUrl(uploadResult.getData());
                    media.setMediaType(determineMediaType(file.getContentType()));
                    diaryMediaMapper.insert(media);
                } else {
                    log.error("Failed to upload file for diary {}: {}", diary.getDiaryId(), uploadResult.getMessage());
                }
            }
        }
        return getDiaryById(diary.getDiaryId());
    }

    @Override
    @Transactional
    public Result<Void> deleteDiary(Long diaryId) {
        Diary diary = diaryMapper.selectById(diaryId);
        if (diary == null) {
            return Result.fail("日记不存在");
        }
        QueryWrapper<DiaryMedia> mediaQuery = new QueryWrapper<>();
        mediaQuery.eq("diary_id", diaryId);
        List<DiaryMedia> mediaList = diaryMediaMapper.selectList(mediaQuery);
        for (DiaryMedia media : mediaList) {
            deletePhysicalFile(media.getMediaUrl());
        }
        diaryMapper.deleteById(diaryId);
        return Result.success();
    }

    @Override
    @Transactional
    public Result<Void> deleteDiaryMedia(Long mediaId) {
        DiaryMedia media = diaryMediaMapper.selectById(mediaId);
        if (media != null) {
            deletePhysicalFile(media.getMediaUrl());
            diaryMediaMapper.deleteById(mediaId);
        }
        return Result.success();
    }

    /**
     * 删除物理文件
     */
    private void deletePhysicalFile(String fileUrl) {
        if (fileUrl == null) return;
        // 假设 fileUrl 形如 /uploads/xxxx.jpg
        String basePath = System.getProperty("user.dir") + "/src/main/resources";
        String filePath = basePath + fileUrl;
        java.io.File file = new java.io.File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public Result<DiaryVO> getDiaryById(Long diaryId) {
        Diary diary = diaryMapper.selectById(diaryId);
        if (diary == null) {
            return Result.fail("Diary not found.");
        }

        // Fetch associated tags
        QueryWrapper<DiaryTag> diaryTagQuery = new QueryWrapper<>();
        diaryTagQuery.eq("diary_id", diaryId);
        List<DiaryTag> diaryTags = diaryTagMapper.selectList(diaryTagQuery);
        List<String> tagNames = Collections.emptyList();
        if (!CollectionUtils.isEmpty(diaryTags)) {
            List<Long> tagIds = diaryTags.stream().map(DiaryTag::getTagId).collect(Collectors.toList());
            List<Tag> tags = tagMapper.selectBatchIds(tagIds);
            tagNames = tags.stream().map(Tag::getTagName).collect(Collectors.toList());
        }

        // Fetch associated media
        QueryWrapper<DiaryMedia> diaryMediaQuery = new QueryWrapper<>();
        diaryMediaQuery.eq("diary_id", diaryId);
        List<DiaryMedia> diaryMediaList = diaryMediaMapper.selectList(diaryMediaQuery);
        List<MediaVO> mediaVOs = diaryMediaList.stream().map(media -> {
            MediaVO vo = new MediaVO();
            BeanUtils.copyProperties(media, vo);
            return vo;
        }).collect(Collectors.toList());


        // Assemble and return VO
        DiaryVO diaryVO = new DiaryVO();
        BeanUtils.copyProperties(diary, diaryVO);
        diaryVO.setTags(tagNames);
        diaryVO.setMedia(mediaVOs);

        return Result.success(diaryVO);
    }

    @Override
    public Result<List<DiaryVO>> findDiaries(QueryDiaryDTO queryDiaryDTO, Long userId) {
        // This will require a more complex custom query in DiaryMapper.xml later to handle filtering by date and tags efficiently.
        // For now, let's just return all diaries for the user.
        QueryWrapper<Diary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).orderByDesc("create_time");
        List<Diary> diaries = diaryMapper.selectList(queryWrapper);

        List<DiaryVO> diaryVOs = diaries.stream()
                .map(diary -> getDiaryById(diary.getDiaryId()).getData())
                .collect(Collectors.toList());

        return Result.success(diaryVOs);
    }
} 