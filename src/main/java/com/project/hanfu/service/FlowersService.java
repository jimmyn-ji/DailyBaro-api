package com.project.hanfu.service;

import com.project.hanfu.model.Flower;
import com.project.hanfu.model.dto.HanfuQueryDTO;
import com.project.hanfu.model.dto.UpdateHanfuImgGuidDTO;
import com.project.hanfu.model.dto.UpdateHanfuInfoDTO;
import com.project.hanfu.model.dto.UpdateUserInfoDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.model.vo.UserInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 鲜花商品 服务层
 *
 * @author: ShanZhu
 * @date: 2024-01-24
 */
public interface FlowersService {

    /**
     * 普通用户查询汉服信息
     * @param hanfuQueryDTO
     * @return
     */
    ResultQuery<HanfuInfoVO> find(HanfuQueryDTO hanfuQueryDTO);


    /**
     * 管理员查询汉服信息
     * @param hanfuQueryDTO
     * @return
     */
    ResultQuery<HanfuInfoVO> selectAllHanfuInfo(HanfuQueryDTO hanfuQueryDTO);

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

    ResultData<HanfuInfoVO> deleteHanfuInfo(UpdateHanfuInfoDTO updateHanfuInfoDTO);

    int add(Flower flower);

}
