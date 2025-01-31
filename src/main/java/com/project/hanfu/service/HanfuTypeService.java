package com.project.hanfu.service;

import com.project.hanfu.model.dto.QueryHanfuDTO;
import com.project.hanfu.model.dto.InsertHanfuTypeDTO;
import com.project.hanfu.model.dto.UpdateHanfuTypeDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.model.vo.HanfuTypeVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface HanfuTypeService {

    /**
     * 管理员查询所有种类
     * @param queryHanfuDTO
     * @return
     */
    ResultData<List<HanfuTypeVO>> selectypeByAdmin(QueryHanfuDTO queryHanfuDTO);

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
