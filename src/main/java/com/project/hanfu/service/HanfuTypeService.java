package com.project.hanfu.service;

import com.project.hanfu.model.dto.QueryHanfuDTO;
import com.project.hanfu.model.dto.InsertHanfuTypeDTO;
import com.project.hanfu.model.dto.UpdateHanfuTypeDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.model.vo.HanfuTypeVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;

public interface HanfuTypeService {

    /**
     * 查询汉服种类信息
     * @param queryHanfuDTO
     * @return
     */
    ResultQuery<HanfuInfoVO> selectAllHanfuType(QueryHanfuDTO queryHanfuDTO);

    /**
     * 增加汉服种类信息
     * @param insertHanfuTypeDTO
     * @return
     */
    ResultData<HanfuTypeVO> insertHanfuType(InsertHanfuTypeDTO insertHanfuTypeDTO);

    /**
     * 删除汉服种类信息
     * @param updateHanfuTypeDTO
     * @return
     */
    ResultData<HanfuTypeVO> deleteHanfuType(UpdateHanfuTypeDTO updateHanfuTypeDTO);

    /**
     * 更新汉服种类信息
     * @param updateHanfuTypeDTO
     * @return
     */
    ResultData<HanfuTypeVO> updateHanfuType(UpdateHanfuTypeDTO updateHanfuTypeDTO);

}
