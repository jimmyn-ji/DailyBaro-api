package com.project.hanfu.controller;

import com.project.hanfu.model.dto.*;
import com.project.hanfu.model.vo.PromotionInfoVO;
import com.project.hanfu.model.vo.PromotionTypeInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.NewProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("new")
public class NewProductsController {

    @Autowired
    private NewProductsService newProductsService;

    /**
     * 添加促销活动
     * @param insertPromotionDTO
     * @return
     */
    @RequestMapping("/createPromotion")
    ResultData<PromotionInfoVO> insertPromotion(@RequestBody InsertPromotionDTO insertPromotionDTO){
        return newProductsService.insertPromotion(insertPromotionDTO);
    }

    /**
     * 更新促销活动
     * @param updatePromotionDTO
     * @return
     */
    @RequestMapping("/updatePromotion")
    ResultData<PromotionInfoVO> updatePromotion(@RequestBody UpdatePromotionDTO updatePromotionDTO){
        return newProductsService.updatePromotion(updatePromotionDTO);
    }

    /**
     * 删除促销活动
     * @param deletePromotionDTO
     * @return
     */
    @RequestMapping("/deletePromotion")
    ResultData<PromotionInfoVO> deletePromotion(DeletePromotionDTO deletePromotionDTO){
        return newProductsService.deletePromotion(deletePromotionDTO);
    }



    /**
     * 更新促销图片————上传图片
     * @param file
     * @return
     */
    @RequestMapping("/updateImg")
    public ResultBase updatePromotionImg(@RequestParam("file") MultipartFile file) {
        return newProductsService.updatePromotionImg(file);
    }

    /**
     * 更新图片guid
     * @param updatePromotionImgGuidDTO
     * @return
     */
    @RequestMapping("/updateImgGuid")
    public ResultBase updatePromotionImgGuid(@RequestBody UpdatePromotionImgGuidDTO updatePromotionImgGuidDTO){
        return newProductsService.updatePromotionImgGuid(updatePromotionImgGuidDTO);
    }

    /**
     * 更新状态 0下架 1上架
     * @param updatePromotionDTO
     * @return
     */
    @RequestMapping("/changeState")
    ResultData<PromotionInfoVO> changeState(@RequestBody UpdatePromotionDTO updatePromotionDTO){
        return newProductsService.changeState(updatePromotionDTO);
    }

    /**
     * 查询所有促销活动
     * @param queryPromotionDTO
     * @return
     */
    @RequestMapping("/findAllPromotions")
    ResultQuery<PromotionInfoVO> selectAllPromotions(QueryPromotionDTO queryPromotionDTO){
        return newProductsService.selectAllPromotions(queryPromotionDTO);
    }


    /**
     * 添加促销类型
     * @param insertPromotionTypeDTO
     * @return
     */
    @RequestMapping("/createPromotionType")
    ResultData<PromotionTypeInfoVO> insertPromotionType(@RequestBody InsertPromotionTypeDTO insertPromotionTypeDTO){
        return newProductsService.insertPromotionType(insertPromotionTypeDTO);
    }

    /**
     * 查询所有促销类型
     * @return
     */
    @RequestMapping("/findAllPromotionTypes")
    ResultQuery<PromotionTypeInfoVO> selectAllPromotionTypes(){
        return newProductsService.selectAllPromotionTypes();
    }
}
