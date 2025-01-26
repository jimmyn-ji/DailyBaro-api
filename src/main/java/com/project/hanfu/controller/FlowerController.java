package com.project.hanfu.controller;

import com.project.hanfu.config.HttpMsg;
import com.project.hanfu.model.Flower;
import com.project.hanfu.menu.StatusCode;
import com.project.hanfu.model.dto.HanfuQueryDTO;
import com.project.hanfu.model.dto.UpdateHanfuImgGuidDTO;
import com.project.hanfu.model.dto.UpdateHanfuInfoDTO;
import com.project.hanfu.model.dto.UpdateUserInfoDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.model.vo.UserInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.FlowersService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**
 * 汉服信息 控制层
 */
@RestController
@RequestMapping("flower")
public class FlowerController {


    @Resource
    private FlowersService flowerService;


    /**
     * 分页查询汉服信息
     * @param page
     * @param searchKey
     * @param searchType
     * @return
     */
    @RequestMapping("/find")
    ResultQuery<HanfuInfoVO> find(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey, @RequestParam("searchType") String searchType) {
        //从 URL 查询字符串中接收数据并转化为 JSON
        HanfuQueryDTO hanfuQueryDTO = new HanfuQueryDTO();
        hanfuQueryDTO.setPage(page);
        hanfuQueryDTO.setSearchKey(searchKey);
        hanfuQueryDTO.setSearchType(searchType);
        return flowerService.find(hanfuQueryDTO);
    }

    /**
     * 管理员查询汉服信息
     * @param page
     * @param searchKey
     * @return
     */
    @RequestMapping("/findAll")
    ResultQuery<HanfuInfoVO> selectAllHanfuInfo(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey){
        //从 URL 查询字符串中接收数据并转化为 JSON
        HanfuQueryDTO hanfuQueryDTO = new HanfuQueryDTO();
        hanfuQueryDTO.setPage(page);
        hanfuQueryDTO.setSearchKey(searchKey);
        return flowerService.selectAllHanfuInfo(hanfuQueryDTO);
    }

    /**
     * 创建鲜花商品
     *
     * @param flower 商品信息
     * @return 结果
     */
    @RequestMapping("/create")
    ResultBase create(@RequestBody Flower flower) {
        ResultBase resultBase = new ResultBase();

        int ans = flowerService.add(flower);
        if (ans == 1) {
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.ADD_FLOWER_OK);
        }

        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.ADD_FLOWER_FAILED);
    }


    /**
     * 管理员更新汉服信息
     * @param updateHanfuInfoDTO
     * @return
     */
    @RequestMapping("/update")
    public ResultData<HanfuInfoVO> updateHanfuInfo(@RequestBody UpdateHanfuInfoDTO updateHanfuInfoDTO){
        return flowerService.updateHanfuInfo(updateHanfuInfoDTO);
    }


    /**
     * 更改汉服状态 上架/下架
     * @param updateHanfuInfoDTO
     * @return
     */
    @RequestMapping("/changeState")
    ResultData<HanfuInfoVO> updateHanfuState(@RequestBody UpdateHanfuInfoDTO updateHanfuInfoDTO) {
        return flowerService.updateHanfuState(updateHanfuInfoDTO);
    }


    /**
     * 更新汉服图片————上传图片
     * @param file
     * @return
     */
    @RequestMapping("/updateImg")
    public ResultBase updateHanfuImg(@RequestBody MultipartFile file) {
        return flowerService.updateHanfuImg(file);
    }

    /**
     * 更新汉服图片————更新图片guid
     * @param guid,hid
     * @return
     */
    @PutMapping("/updateImgGuid")
    ResultBase updateHanfuImgGuid(@RequestParam("guid") String guid, @RequestParam("id") Long hid) {
        UpdateHanfuImgGuidDTO updateHanfuImgGuidDTO = new UpdateHanfuImgGuidDTO();
        updateHanfuImgGuidDTO.setGuid(guid);
        updateHanfuImgGuidDTO.setHid(hid);
        return flowerService.updateHanfuImgGuid(updateHanfuImgGuidDTO);
    }

    @RequestMapping("/delete")
    ResultData<HanfuInfoVO> deleteHanfuInfo(@RequestParam("id")Long hid){
        UpdateHanfuInfoDTO updateHanfuInfoDTO = new UpdateHanfuInfoDTO();
        updateHanfuInfoDTO.setHid(hid);
        return flowerService.deleteHanfuInfo(updateHanfuInfoDTO);
    }
}

