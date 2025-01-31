package com.project.hanfu.controller;

import com.project.hanfu.model.dto.QueryHanfuDTO;
import com.project.hanfu.model.dto.UpdateHanfuTypeDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.model.dto.InsertHanfuTypeDTO;
import com.project.hanfu.model.vo.HanfuTypeVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.HanfuTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 汉服种类控制层
 */
@RestController
@RequestMapping("species")
public class HanfuTypeController {

    @Autowired
    private HanfuTypeService hanfuTypeService;


    /**
     * 管理员查询所有种类
     * @param queryHanfuDTO
     * @return
     */
    @RequestMapping("/findTypebyAdmin")
    ResultData<List<HanfuTypeVO>> selectypeByAdmin(QueryHanfuDTO queryHanfuDTO) {
        return hanfuTypeService.selectypeByAdmin(queryHanfuDTO);
    }

    /**
     * 模糊分页查询所有种类
     *
     * @param page
     * @param searchKey
     * @return
     */
    @RequestMapping("/find")
    ResultQuery<HanfuInfoVO> selectAllHanfuType(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey) {
        //从 URL 查询字符串中接收数据并转化为 JSON
        QueryHanfuDTO queryHanfuDTO = new QueryHanfuDTO();
        queryHanfuDTO.setPage(page);
        queryHanfuDTO.setSearchKey(searchKey);
        return hanfuTypeService.selectAllHanfuType(queryHanfuDTO);
    }


    /**
     * 添加汉服种类信息
     * @param insertHanfuTypeDTO
     * @return
     */
    @RequestMapping("/create")
    ResultData<HanfuTypeVO> insertHanfuType(@RequestBody InsertHanfuTypeDTO insertHanfuTypeDTO) {
        return hanfuTypeService.insertHanfuType(insertHanfuTypeDTO);
    }


    /**
     * 更新汉服种类信息
     * @param updateHanfuTypeDTO
     * @return
     */
    @RequestMapping("/update")
    ResultData<HanfuTypeVO> updateHanfuType(@RequestBody UpdateHanfuTypeDTO updateHanfuTypeDTO) {
        return hanfuTypeService.updateHanfuType(updateHanfuTypeDTO);
    }

    /**
     * 删除汉服种类信息
     * @param htid
     * @return
     */
    @RequestMapping("/delete")
    ResultData<HanfuTypeVO> deleteHanfuType(@RequestParam("htid") Long htid) {
        UpdateHanfuTypeDTO updateHanfuTypeDTO = new UpdateHanfuTypeDTO();
        updateHanfuTypeDTO.setHtid(htid);
        return hanfuTypeService.deleteHanfuType(updateHanfuTypeDTO);
    }

}

