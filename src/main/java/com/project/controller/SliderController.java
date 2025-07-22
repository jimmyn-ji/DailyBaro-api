package com.project.controller;

import com.github.pagehelper.PageInfo;
import com.project.model.Slider;
import com.project.model.dto.SliderDTO;
import com.project.model.vo.SliderVO;
import com.project.service.SliderService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/slider")
public class SliderController {

    @Autowired
    private SliderService sliderService;

    @RequestMapping("/getSliderByPage")
    public Result<PageInfo<Slider>> getSliderByPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Slider> pageInfo = sliderService.getSliderByPage(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @RequestMapping("/getSliderById/{sliderId}")
    public Result<Slider> getSliderById(@PathVariable Long sliderId) {
        Slider slider = sliderService.getSliderById(sliderId);
        return Result.success(slider);
    }

    @RequestMapping(value = "/add")
    public Result<SliderVO> addSlider(SliderDTO sliderDTO) {
        return sliderService.addSlider(sliderDTO);
    }

    @RequestMapping("/update")
    public Result<SliderDTO> updateSlider(SliderDTO sliderDTO) {
        sliderService.updateSlider(sliderDTO);
        return Result.success(sliderDTO);
    }

    @DeleteMapping("/delete/{sliderId}")
    public Result<Void> deleteSlider(@PathVariable Long sliderId) {
        sliderService.deleteSlider(sliderId);
        return Result.success();
    }
}