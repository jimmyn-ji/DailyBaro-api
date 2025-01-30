package com.project.hanfu.service.impl;

import com.project.hanfu.exception.CustomException;
import com.project.hanfu.mapper.HanfuMapper;
import com.project.hanfu.model.Hanfu;
import com.project.hanfu.model.dto.QueryHanfuDTO;
import com.project.hanfu.model.dto.UpdateHanfuImgGuidDTO;
import com.project.hanfu.model.dto.UpdateHanfuInfoDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.HanfuService;
import com.project.hanfu.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HanfuServiceImpl implements HanfuService {

    @Value("${uploadPath}")
    private String uploadPath;

    @Autowired
    private HanfuMapper hanfuMapper;



    /**
     * 查询汉服信息
     * @param queryHanfuDTO
     * @return
     */
    @Override
    public ResultQuery<HanfuInfoVO> find(QueryHanfuDTO queryHanfuDTO) {
        String searchKey = queryHanfuDTO.getSearchKey();
        String searchType = queryHanfuDTO.getSearchType();

        //获取查询结果 根据汉服名/汉服种类进行模糊查询 且state为1 的数据
        Example hanfuExample = new Example(Hanfu.class);
        hanfuExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("state",1)
                .andLike("hanfuName","%"+searchKey+"%")
                .andLike("hanfuType","%"+searchType+"%");
        List<Hanfu> hanfus = hanfuMapper.selectByExample(hanfuExample);

        if(CollectionUtils.isEmpty(hanfus)){
            return ResultUtil.getResultQuery(new ArrayList<>(),0);
        }

        List<HanfuInfoVO> hanfuInfoVOS = hanfus.stream().map(hanfu -> {
            HanfuInfoVO hanfuInfoVO = new HanfuInfoVO();
            BeanUtils.copyProperties(hanfu,hanfuInfoVO);
            return hanfuInfoVO;
        }).collect(Collectors.toList());

        return ResultUtil.getResultQuery(hanfuInfoVOS,hanfus.size());
    }

    /**
     * 管理员查询汉服信息
     * @param queryHanfuDTO
     * @return
     */
    @Override
    public ResultQuery<HanfuInfoVO> selectAllHanfuInfo(QueryHanfuDTO queryHanfuDTO) {
        String searchKey = queryHanfuDTO.getSearchKey();

        Example hanfuExample = new Example(Hanfu.class);
        hanfuExample.createCriteria().andEqualTo("isdelete",0)
                .andLike("hanfuName","%"+searchKey+"%");
        List<Hanfu> hanfus = hanfuMapper.selectByExample(hanfuExample);
        if(CollectionUtils.isEmpty(hanfus)){
            return ResultUtil.getResultQuery(new ArrayList<>(),0);
        }

        List<HanfuInfoVO> hanfuInfoVOS = hanfus.stream().map(hanfu -> {
            HanfuInfoVO hanfuInfoVO = new HanfuInfoVO();
            BeanUtils.copyProperties(hanfu,hanfuInfoVO);
            return hanfuInfoVO;
        }).collect(Collectors.toList());

        return ResultUtil.getResultQuery(hanfuInfoVOS,hanfus.size());
    }

    /**
     * 更新汉服信息
     * @param updateHanfuInfoDTO
     * @return
     */
    @Override
    public ResultData<HanfuInfoVO> updateHanfuInfo(UpdateHanfuInfoDTO updateHanfuInfoDTO) {
        //获取汉服id
        Long hid = updateHanfuInfoDTO.getHid();

        //根据id查询汉服信息
        Example hanfuExample = new Example(Hanfu.class);
        hanfuExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("hid",hid);
        List<Hanfu> hanfus = hanfuMapper.selectByExample(hanfuExample);

        //更新汉服信息
        Hanfu hanfu = hanfus.get(0);
        hanfu.setHanfuName(updateHanfuInfoDTO.getHanfuName());
        hanfu.setHanfuType(updateHanfuInfoDTO.getHanfuType());
        hanfu.setPrice(updateHanfuInfoDTO.getPrice());
        hanfu.setHanfuDetail(updateHanfuInfoDTO.getHanfuDetail());
        hanfuMapper.updateByExampleSelective(hanfu,hanfuExample);

        //返回VO
        HanfuInfoVO hanfuInfoVO =new HanfuInfoVO();
        BeanUtils.copyProperties(hanfu,hanfuInfoVO);
        return ResultUtil.getResultData(hanfuInfoVO);
    }

    /**
     * 更新汉服状态 0下架 1上架
     * @param updateHanfuInfoDTO
     * @return
     */
    @Override
    public ResultData<HanfuInfoVO> updateHanfuState(UpdateHanfuInfoDTO updateHanfuInfoDTO) {
        //获取汉服id
        Long hid = updateHanfuInfoDTO.getHid();
        Integer state = updateHanfuInfoDTO.getState();

        //根据id查询汉服信息
        Example hanfuExample = new Example(Hanfu.class);
        hanfuExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("hid",hid);
        List<Hanfu> hanfus = hanfuMapper.selectByExample(hanfuExample);
        Hanfu hanfu = hanfus.get(0);

        //更新汉服信息
        hanfu.setState(state);
        hanfuMapper.updateByExampleSelective(hanfu,hanfuExample);

        //返回VO
        HanfuInfoVO hanfuInfoVO =new HanfuInfoVO();
        BeanUtils.copyProperties(hanfu,hanfuInfoVO);
        return ResultUtil.getResultData(hanfuInfoVO);
    }

    /**
     * 更新汉服图片
     * @param file
     * @return
     */
    @Override
    public ResultBase updateHanfuImg(MultipartFile file) {
        //获取文件名
        String filename = file.getOriginalFilename();

        //只接收 jpg/png 图片 否则报错
        if (!filename.endsWith(".jpg") && !filename.endsWith(".png")) {
            throw new CustomException("文件类型错误");
        }

        String imgGuid = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";

        try {
            savePic(file.getInputStream(), imgGuid);
            return ResultUtil.getResultBase(imgGuid);
        } catch (IOException e) {
            throw new CustomException("更新图片失败");
        }

    }

    /**
     * 更新汉服图片guid
     * @param updateHanfuImgGuidDTO
     * @return
     */
    @Override
    public ResultBase updateHanfuImgGuid(UpdateHanfuImgGuidDTO updateHanfuImgGuidDTO) {
        String guid = updateHanfuImgGuidDTO.getGuid();
        Long hid = updateHanfuImgGuidDTO.getHid();

        Example hanfuExample = new Example(Hanfu.class);
        hanfuExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("hid",hid);
        List<Hanfu> hanfus = hanfuMapper.selectByExample(hanfuExample);
        Hanfu hanfu = hanfus.get(0);

        hanfu.setImgGuid(guid);
        hanfuMapper.updateByExampleSelective(hanfu,hanfuExample);

        return ResultUtil.getResultBase();
    }

    /**
     * 删除汉服信息
     * @param updateHanfuInfoDTO
     * @return
     */
    @Override
    public ResultData<HanfuInfoVO> deleteHanfuInfo(UpdateHanfuInfoDTO updateHanfuInfoDTO) {
        //获取汉服id
        Long hid = updateHanfuInfoDTO.getHid();

        //根据id查询汉服信息
        Example hanfuExample = new Example(Hanfu.class);
        hanfuExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("hid",hid);
        List<Hanfu> hanfus = hanfuMapper.selectByExample(hanfuExample);
        Hanfu hanfu = hanfus.get(0);
        //更新汉服信息
        hanfu.setIsdelete(1);
        hanfuMapper.updateByExampleSelective(hanfu,hanfuExample);
        //返回VO
        HanfuInfoVO hanfuInfoVO =new HanfuInfoVO();
        BeanUtils.copyProperties(hanfu,hanfuInfoVO);
        return ResultUtil.getResultData(hanfuInfoVO);
    }


    private void savePic(InputStream inputStream, String fileName) {
        OutputStream os = null;
        try {
            String path = uploadPath;
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            os = new FileOutputStream(new File(path + fileName));
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
