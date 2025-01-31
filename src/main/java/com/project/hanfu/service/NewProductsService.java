package com.project.hanfu.service;

import com.project.hanfu.model.PromotionType;
import com.project.hanfu.model.dto.*;
import com.project.hanfu.model.vo.PromotionInfoVO;
import com.project.hanfu.model.vo.PromotionTypeInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;

public interface NewProductsService {


    /**
     * 新增促销活动
     * @param insertPromotionDTO
     * @return
     */
    ResultData<PromotionInfoVO> insertPromotion(InsertPromotionDTO insertPromotionDTO);

    /**
     * 更新促销活动
     * @param updatePromotionDTO
     * @return
     */
    ResultData<PromotionInfoVO> updatePromotion(UpdatePromotionDTO updatePromotionDTO);


    /**
     * 删除促销活动
     * @param deletePromotionDTO
     * @return
     */
    ResultData<PromotionInfoVO> deletePromotion(DeletePromotionDTO deletePromotionDTO);

    ResultBase updateImgGuid(UpdatePromotionImgGuidDTO updatePromotionImgGuidDTO);

    ResultData<PromotionInfoVO> changeState(UpdatePromotionDTO updatePromotionDTO);

    /**
     * 分页查询所有促销活动
     * @param queryPromotionDTO
     * @return
     */
    ResultQuery<PromotionInfoVO> selectAllPromotions(QueryPromotionDTO queryPromotionDTO);

    /**
     * 新增促销类型
     * @param insertPromotionTypeDTO
     * @return
     */
    ResultData<PromotionTypeInfoVO> insertPromotionType(InsertPromotionTypeDTO insertPromotionTypeDTO);

    /**
     *
     * @return
     */
    ResultQuery<PromotionTypeInfoVO> selectAllPromotionTypes();

}
