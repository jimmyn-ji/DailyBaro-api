package com.project.hanfu.service.impl;

import com.project.hanfu.exception.CustomException;
import com.project.hanfu.mapper.HanfuTypeMapper;
import com.project.hanfu.mapper.SpeciesDao;
import com.project.hanfu.model.Hanfu;
import com.project.hanfu.model.HanfuType;
import com.project.hanfu.model.Species;
import com.project.hanfu.model.dto.HanfuQueryDTO;
import com.project.hanfu.model.dto.InsertHanfuTypeDTO;
import com.project.hanfu.model.dto.UpdateHanfuTypeDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.model.vo.HanfuTypeVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.SpeciesService;
import com.project.hanfu.util.CollectionUtils;
import com.project.hanfu.util.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    @Resource
    private SpeciesDao speciesDao;

    @Autowired
    private HanfuTypeMapper hanfuTypeMapper;
    @Autowired
    private SnowFlake snowFlake;

    /**
     * 查询所有汉服种类信息
     * @param hanfuQueryDTO
     * @return
     */
    @Override
    public ResultQuery<HanfuInfoVO> selectAllHanfuType(HanfuQueryDTO hanfuQueryDTO) {
        String searchKey = hanfuQueryDTO.getSearchKey();

        Example hanfuTypeExample = new Example(HanfuType.class);
        hanfuTypeExample.createCriteria().andEqualTo("isdelete",0)
                .andLike("hanfuType","%"+searchKey+"%");
        List<HanfuType> hanfuTypeList = hanfuTypeMapper.selectByExample(hanfuTypeExample);

        //如果查询结果为空
        if(CollectionUtils.isEmpty(hanfuTypeList)){
            return ResultUtil.getResultQuery(new ArrayList<>(),0);
        }

        //若不为空
        List<HanfuInfoVO> hanfuInfoVOS = hanfuTypeList.stream().map(hanfuType -> {
            HanfuInfoVO hanfuInfoVO = new HanfuInfoVO();
            hanfuInfoVO.setHtid(hanfuType.getHtid());
            BeanUtils.copyProperties(hanfuType,hanfuInfoVO);
            return hanfuInfoVO;
        }).collect(Collectors.toList());

        return ResultUtil.getResultQuery(hanfuInfoVOS,hanfuInfoVOS.size());
    }

    /**
     * 增加汉服种类信息
     * @param insertHanfuTypeDTO
     * @return
     */
    @Override
    public ResultData<HanfuTypeVO> insertHanfuType(InsertHanfuTypeDTO insertHanfuTypeDTO) {
        //获取汉服类型
        String hanfuTypeName = insertHanfuTypeDTO.getHanfuType();

        //查询是否已存在
        Example hanfuTypeExample = new Example(HanfuType.class);
        hanfuTypeExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("hanfuType",hanfuTypeName);
        List<HanfuType> hanfuTypeList = hanfuTypeMapper.selectByExample(hanfuTypeExample);

        //如果已存在 抛出异常
        if(CollectionUtils.isNotEmpty(hanfuTypeList)){
            throw new CustomException("该汉服类型已存在");
        }
        //不存在，插入数据
        HanfuType hanfuType = new HanfuType();
        hanfuType.setHtid(snowFlake.nextId());
        BeanUtils.copyProperties(insertHanfuTypeDTO,hanfuType);
        hanfuTypeMapper.insertSelective(hanfuType);

        //返回
        HanfuTypeVO hanfuTypeVO = new HanfuTypeVO();
        BeanUtils.copyProperties(hanfuType,hanfuTypeVO);
        return ResultUtil.getResultData(hanfuTypeVO);
    }

    /**
     * 删除汉服种类信息
     * @param updateHanfuTypeDTO
     * @return
     */
    @Override
    @Transactional
    public ResultData<HanfuTypeVO> deleteHanfuType(UpdateHanfuTypeDTO updateHanfuTypeDTO) {
        //获取汉服种类id
        Long htid = updateHanfuTypeDTO.getHtid();

        //查询是否存在
        Example hanfuTypeExample = new Example(HanfuType.class);
        hanfuTypeExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("htid",htid);
        List<HanfuType> hanfuTypeList = hanfuTypeMapper.selectByExample(hanfuTypeExample);

        HanfuType hanfuType = hanfuTypeList.get(0);
        //如果不存在 抛出异常
        if(CollectionUtils.isEmpty(hanfuTypeList)){
            throw new CustomException("该汉服类型不存在");
        }
        //存在，删除数据
        hanfuType.setIsdelete(1);
        hanfuTypeMapper.updateByExampleSelective(hanfuType, hanfuTypeExample);

        //返回VO
        HanfuTypeVO hanfuTypeVO = new HanfuTypeVO();
        BeanUtils.copyProperties(hanfuType,hanfuTypeVO);
        return ResultUtil.getResultData(hanfuTypeVO);
    }



    @Override
    public int update(Species species) {
        return speciesDao.update(species);
    }

//    @Override
//    public List<Species> find(String searchKey) {
//        return speciesDao.find(searchKey);
//    }

    @Override
    public List<Species> findAll() {
        return speciesDao.findAll();
    }
}
