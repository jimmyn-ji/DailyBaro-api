package com.project.hanfu.service;

import com.project.hanfu.model.Flower;
import com.project.hanfu.model.dto.HanfuQueryDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;

import java.util.List;

/**
 * 鲜花商品 服务层
 *
 * @author: ShanZhu
 * @date: 2024-01-24
 */
public interface FlowersService {

    /**
     * 查询汉服信息
     * @param hanfuQueryDTO
     * @return
     */
    ResultQuery<HanfuInfoVO> find(HanfuQueryDTO hanfuQueryDTO);


    int add(Flower flower);
    int delete(int id);
    int update(Flower flower);
    List<Flower> findAll(String searchKey);
    int updateImg(String img_guid,Integer id);
}
