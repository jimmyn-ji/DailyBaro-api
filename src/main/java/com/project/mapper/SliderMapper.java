package com.project.mapper;

import com.project.model.Slider;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SliderMapper {
    List<Slider> selectAllSliders();
    Slider selectById(Long sliderId);
    int insertSlider(Slider slider);
    int updateSlider(Slider slider);
    int deleteSlider(Long sliderId);
}