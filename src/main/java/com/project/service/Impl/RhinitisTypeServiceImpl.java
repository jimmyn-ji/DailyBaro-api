package com.project.service.Impl;

import com.project.controller.FileUploadController;
import com.project.mapper.RhinitisTypeMapper;
import com.project.model.RhinitisType;
import com.project.model.dto.InsertRhinitisTypeDTO;
import com.project.model.dto.QueryRhinitisDTO;
import com.project.model.dto.UpdateRhinitisTypeDTO;
import com.project.model.vo.RhinitisTypeVO;
import com.project.service.RhinitisTypeService;
import com.project.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RhinitisTypeServiceImpl implements RhinitisTypeService {

    @Autowired
    private RhinitisTypeMapper rhinitisTypeMapper;

    @Autowired
    private FileUploadController fileUploadController;

    @Override
    public Result<List<RhinitisTypeVO>> selectAllRhinitisType(QueryRhinitisDTO queryRhinitisDTO) {
        List<RhinitisType> rhinitisTypes = rhinitisTypeMapper.selectAllRhinitisTypes();
        List<RhinitisTypeVO> vos = rhinitisTypes.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return Result.success(vos);
    }

    @Override
    public Result<RhinitisTypeVO> getById(Long rid) {
        RhinitisType rhinitisType = rhinitisTypeMapper.getRhinitisTypeById(rid);
        if (rhinitisType == null) {
            return Result.fail("未找到该鼻炎类型");
        }
        return Result.success(convertToVO(rhinitisType));
    }

    @Override
    public Result<RhinitisTypeVO> insertRhinitisType(InsertRhinitisTypeDTO insertDTO) {
        RhinitisType rhinitisType = new RhinitisType();
        BeanUtils.copyProperties(insertDTO, rhinitisType);

        // 处理图片上传
        if (insertDTO.getImageFile() != null && !insertDTO.getImageFile().isEmpty()) {
            Result<String> uploadResult = fileUploadController.uploadImage(insertDTO.getImageFile());
            if (uploadResult.getCode() != 200) {
                return Result.fail(uploadResult.getMessage());
            }
            rhinitisType.setImageGuid(uploadResult.getData());
        }

        rhinitisTypeMapper.insertRhinitisType(rhinitisType);
        return Result.success(convertToVO(rhinitisType));
    }

    @Override
    public Result<RhinitisTypeVO> updateRhinitisType(UpdateRhinitisTypeDTO updateDTO) {
        RhinitisType rhinitisType = rhinitisTypeMapper.getRhinitisTypeById(updateDTO.getRid());
        if (rhinitisType == null) {
            return Result.fail("鼻炎类型不存在");
        }

        // 处理图片更新
        if (updateDTO.getImageFile() != null && !updateDTO.getImageFile().isEmpty()) {
            // 上传新图片
            Result<String> uploadResult = fileUploadController.uploadImage(updateDTO.getImageFile());
            if (uploadResult.getCode() != 200) {
                return Result.fail(uploadResult.getMessage());
            }
            String newImageUrl = uploadResult.getData();

            rhinitisType.setImageGuid(newImageUrl);
        }

        // 更新其他字段
        BeanUtils.copyProperties(updateDTO, rhinitisType);
        rhinitisTypeMapper.updateRhinitisType(rhinitisType);

        return Result.success(convertToVO(rhinitisType));
    }

    @Override
    public Result<Void> deleteRhinitisType(Long rid) {
        rhinitisTypeMapper.deleteRhinitisType(rid);
        return Result.success();
    }

    private RhinitisTypeVO convertToVO(RhinitisType rhinitisType) {
        RhinitisTypeVO vo = new RhinitisTypeVO();
        BeanUtils.copyProperties(rhinitisType, vo);
        return vo;
    }
}