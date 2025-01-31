package com.project.hanfu.controller;

import com.project.hanfu.model.dto.InsertHanfuInfoDTO;
import com.project.hanfu.model.dto.QueryHanfuDTO;
import com.project.hanfu.model.dto.UpdateHanfuImgGuidDTO;
import com.project.hanfu.model.dto.UpdateHanfuInfoDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.HanfuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 汉服信息 控制层
 */
@RestController
@RequestMapping("flower")
public class HanfuController {

    @Autowired
    private HanfuService hanfuService;

    @RequestMapping("/create")
    public ResultData<HanfuInfoVO> insertHanfuInfo(@RequestBody InsertHanfuInfoDTO insertHanfuInfoDTO){
        return hanfuService.insertHanfuInfo(insertHanfuInfoDTO);
    }
    
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
        QueryHanfuDTO queryHanfuDTO = new QueryHanfuDTO();
        queryHanfuDTO.setPage(page);
        queryHanfuDTO.setSearchKey(searchKey);
        queryHanfuDTO.setSearchType(searchType);
        return hanfuService.find(queryHanfuDTO);
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
        QueryHanfuDTO queryHanfuDTO = new QueryHanfuDTO();
        queryHanfuDTO.setPage(page);
        queryHanfuDTO.setSearchKey(searchKey);
        return hanfuService.selectAllHanfuInfo(queryHanfuDTO);
    }



    /**
     * 管理员更新汉服信息
     * @param updateHanfuInfoDTO
     * @return
     */
    @RequestMapping("/update")
    public ResultData<HanfuInfoVO> updateHanfuInfo(@RequestBody UpdateHanfuInfoDTO updateHanfuInfoDTO){
        return hanfuService.updateHanfuInfo(updateHanfuInfoDTO);
    }


    /**
     * 更改汉服状态 上架/下架
     * @param updateHanfuInfoDTO
     * @return
     */
    @RequestMapping("/changeState")
    ResultData<HanfuInfoVO> updateHanfuState(@RequestBody UpdateHanfuInfoDTO updateHanfuInfoDTO) {
        return hanfuService.updateHanfuState(updateHanfuInfoDTO);
    }


    /**
     * 更新汉服图片————上传图片
     * @param file
     * @return
     */
    @RequestMapping("/updateImg")
    public ResultBase updateHanfuImg(@RequestBody MultipartFile file) {
        return hanfuService.updateHanfuImg(file);
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
        return hanfuService.updateHanfuImgGuid(updateHanfuImgGuidDTO);
    }


    /**
     * 删除汉服信息
     * @param hid
     * @return
     */
    @RequestMapping("/delete")
    ResultData<HanfuInfoVO> deleteHanfuInfo(@RequestParam("id")Long hid){
        UpdateHanfuInfoDTO updateHanfuInfoDTO = new UpdateHanfuInfoDTO();
        updateHanfuInfoDTO.setHid(hid);
        return hanfuService.deleteHanfuInfo(updateHanfuInfoDTO);
    }
}

