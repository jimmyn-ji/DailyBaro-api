package com.project.hanfu.service.impl;

import com.project.hanfu.mapper.FlowersDao;
import com.project.hanfu.mapper.HanfuMapper;
import com.project.hanfu.model.Flower;
import com.project.hanfu.model.Hanfu;
import com.project.hanfu.model.dto.HanfuQueryDTO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.FlowersService;
import com.project.hanfu.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlowersServiceImpl implements FlowersService {


    @Autowired
    private HanfuMapper hanfuMapper;

    @Resource
    private FlowersDao flowersDao;

    @Override
    public int add(Flower flower) {
        return flowersDao.add(flower);
    }

    @Override
    public int delete(int id) {
        return flowersDao.delete(id);
    }

    @Override
    public int update(Flower flower) {
        return flowersDao.update(flower);
    }

    @Override
    public List<Flower> findAll(String searchKey) {
        return flowersDao.findAll(searchKey);
    }

    @Override
    public int updateImg(String img_guid,Integer id) {
        return flowersDao.updateImg(img_guid,id);
    }


    /**
     * 查询汉服信息
     * @param hanfuQueryDTO
     * @return
     */
    @Override
    public ResultQuery<HanfuInfoVO> find(HanfuQueryDTO hanfuQueryDTO) {
        String searchKey = hanfuQueryDTO.getSearchKey();
        String searchType = hanfuQueryDTO.getSearchType();

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
}
