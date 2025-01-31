package com.project.hanfu.service;

import com.project.hanfu.model.dto.InsertHanfuInfoDTO;
import com.project.hanfu.model.dto.QueryHanfuDTO;
import com.project.hanfu.model.dto.UpdateHanfuImgGuidDTO;
import com.project.hanfu.model.dto.UpdateHanfuInfoDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import org.springframework.web.multipart.MultipartFile;

/**
 * 汉服信息 服务层
 */
public interface HanfuService {

    /**
     * 添加汉服信息
     * @param insertHanfuInfoDTO
     * @return
     */
    ResultData<HanfuInfoVO> insertHanfuInfo(InsertHanfuInfoDTO insertHanfuInfoDTO);

    /**
     * 普通用户查询汉服信息
     * @param queryHanfuDTO
     * @return
     */
    ResultQuery<HanfuInfoVO> find(QueryHanfuDTO queryHanfuDTO);


    /**
     * 管理员查询汉服信息
     * @param queryHanfuDTO
     * @return
     */
    ResultQuery<HanfuInfoVO> selectAllHanfuInfo(QueryHanfuDTO queryHanfuDTO);

    /**
     * 管理员更新汉服信息
     * @param updateHanfuInfoDTO
     * @return
     */
    ResultData<HanfuInfoVO> updateHanfuInfo(UpdateHanfuInfoDTO updateHanfuInfoDTO);

    /**
     * 修改汉服上架/下架状态
     * @param updateHanfuInfoDTO
     * @return
     */
    ResultData<HanfuInfoVO> updateHanfuState(UpdateHanfuInfoDTO updateHanfuInfoDTO);

    /**
     * 更新汉服图片————上传图片
     * @param file
     * @return
     */
    ResultBase updateHanfuImg(MultipartFile file);

    /**
     * 更新汉服图片guid————更新图片
     * @param updateHanfuImgGuidDTO
     * @return
     */
    ResultBase updateHanfuImgGuid(UpdateHanfuImgGuidDTO updateHanfuImgGuidDTO);

    /**
     * 管理员删除汉服信息
     * @param updateHanfuInfoDTO
     * @return
     */
    ResultData<HanfuInfoVO> deleteHanfuInfo(UpdateHanfuInfoDTO updateHanfuInfoDTO);


}
