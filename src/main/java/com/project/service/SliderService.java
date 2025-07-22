package com.project.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.controller.FileUploadController;
import com.project.mapper.SliderMapper;
import com.project.model.Slider;
import com.project.model.dto.SliderDTO;
import com.project.model.vo.SliderVO;
import com.project.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SliderService {

    @Autowired
    private SliderMapper sliderMapper;

    @Autowired
    private FileUploadController fileUploadController;

    public PageInfo<Slider> getSliderByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Slider> sliders = sliderMapper.selectAllSliders();
        return new PageInfo<>(sliders);
    }

    public Slider getSliderById(Long sliderId) {
        return sliderMapper.selectById(sliderId);
    }

    public Result<SliderVO> addSlider(SliderDTO sliderDTO) {
        Slider slider = new Slider();
        BeanUtils.copyProperties(sliderDTO, slider);

        // 处理图片上传
        if (sliderDTO.getImageFile() != null && !sliderDTO.getImageFile().isEmpty()) {
            Result<String> uploadResult = fileUploadController.uploadImage(sliderDTO.getImageFile());
            if (uploadResult.getCode() != 200) {
                return Result.fail(uploadResult.getMessage());
            }
            slider.setImageUrl(uploadResult.getData());
        }

        sliderMapper.insertSlider(slider);
        return Result.success(convertToVO(slider));
    }

    public Result<SliderVO> updateSlider(SliderDTO sliderDTO) {
        Slider existingSlider = sliderMapper.selectById(sliderDTO.getSliderId());
        if (existingSlider == null) {
            return Result.fail("轮播图不存在");
        }

        // 处理图片更新
        if (sliderDTO.getImageFile() != null && !sliderDTO.getImageFile().isEmpty()) {
            // 上传新图片
            Result<String> uploadResult = fileUploadController.uploadImage(sliderDTO.getImageFile());
            if (uploadResult.getCode() != 200) {
                return Result.fail(uploadResult.getMessage());
            }
            String newImageUrl = uploadResult.getData();

            sliderDTO.setImageUrl(newImageUrl);
        }

        BeanUtils.copyProperties(sliderDTO, existingSlider);
        sliderMapper.updateSlider(existingSlider);
        return Result.success(convertToVO(existingSlider));
    }

    public Result<Void> deleteSlider(Long sliderId) {
        sliderMapper.deleteSlider(sliderId);
        return Result.success();
    }

    private SliderVO convertToVO(Slider slider) {
        SliderVO vo = new SliderVO();
        BeanUtils.copyProperties(slider, vo);
        return vo;
    }
}    