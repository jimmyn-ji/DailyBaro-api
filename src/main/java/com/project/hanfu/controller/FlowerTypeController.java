package com.project.hanfu.controller;

import com.project.hanfu.config.HttpMsg;
import com.project.hanfu.menu.StatusCode;
import com.project.hanfu.model.dto.HanfuQueryDTO;
import com.project.hanfu.model.dto.UpdateHanfuTypeDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.model.dto.InsertHanfuTypeDTO;
import com.project.hanfu.model.vo.HanfuTypeVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.model.Species;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.SpeciesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 鲜花种类 控制层
 *
 * @author: ShanZhu
 * @date: 2024-01-24
 */
@RestController
@RequestMapping("species")
public class FlowerTypeController {

    @Resource
    private SpeciesService speciesService;

    /**
     * 查询所有种类
     *
     * @return
     */
    @RequestMapping("/findAll")
    ResultBase findAll() {
        ResultBase resultBase = new ResultBase();
        List<Species> all = speciesService.findAll();
        if (all.size() <= 0) {
            return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.NO_TYPE_NOW);
        }
        return resultBase.setCode(StatusCode.SUCCESS).setData(all);
    }

    /**
     * 分页查询所有种类
     *
     * @param page
     * @param searchKey
     * @return
     */
    @RequestMapping("/find")
    ResultQuery<HanfuInfoVO> selectAllHanfuType(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey) {
        //从 URL 查询字符串中接收数据并转化为 JSON
        HanfuQueryDTO hanfuQueryDTO = new HanfuQueryDTO();
        hanfuQueryDTO.setPage(page);
        hanfuQueryDTO.setSearchKey(searchKey);
        return speciesService.selectAllHanfuType(hanfuQueryDTO);
    }


    /**
     * 添加汉服种类信息
     * @param insertHanfuTypeDTO
     * @return
     */
    @RequestMapping("/create")
    ResultData<HanfuTypeVO> insertHanfuType(@RequestBody InsertHanfuTypeDTO insertHanfuTypeDTO) {
        return speciesService.insertHanfuType(insertHanfuTypeDTO);
    }


    @RequestMapping("/update")
    ResultBase update(@RequestBody Species species) {
        ResultBase resultBase = new ResultBase();
        try {
            speciesService.update(species);
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.UPDATE_TYPE_OK);
        } catch (Exception e) {
            return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.UPDATE_TYPE_FAILED);
        }
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
        return speciesService.deleteHanfuType(updateHanfuTypeDTO);
    }

}

