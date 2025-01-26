package com.project.hanfu.service;

import com.project.hanfu.model.Species;
import com.project.hanfu.model.dto.HanfuQueryDTO;
import com.project.hanfu.model.dto.InsertHanfuTypeDTO;
import com.project.hanfu.model.dto.UpdateHanfuTypeDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.model.vo.HanfuTypeVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SpeciesService {

    /**
     * 查询汉服种类信息
     * @param hanfuQueryDTO
     * @return
     */
    ResultQuery<HanfuInfoVO> selectAllHanfuType(HanfuQueryDTO hanfuQueryDTO);

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

    int update(Species species);
    List<Species> findAll();
}
