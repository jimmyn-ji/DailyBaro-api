package com.project.hanfu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.hanfu.exception.CustomException;
import com.project.hanfu.mapper.HanfuMapper;
import com.project.hanfu.mapper.PromotionMapper;
import com.project.hanfu.mapper.PromotionTypeMapper;
import com.project.hanfu.model.Hanfu;
import com.project.hanfu.model.Promotion;
import com.project.hanfu.model.PromotionType;
import com.project.hanfu.model.dto.*;
import com.project.hanfu.model.vo.PromotionInfoVO;
import com.project.hanfu.model.vo.PromotionTypeInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.NewProductsService;
import com.project.hanfu.util.CollectionUtils;
import com.project.hanfu.util.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NewProductsServiceImpl implements NewProductsService {

    @Value("${uploadPath}")
    private String uploadPath;

    @Autowired
    private HanfuMapper hanfuMapper;

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private PromotionTypeMapper promotionTypeMapper;

    @Autowired
    private SnowFlake snowFlake;

    /**
     * 新增促销商品
     * @param insertPromotionDTO
     * @return
     */
    @Override
    @Transactional
    public ResultData<PromotionInfoVO> insertPromotion(InsertPromotionDTO insertPromotionDTO) {

        //获取促销活动名称
        String promotionName = insertPromotionDTO.getPromotionName();
        //获取促销活动类型
        String promotionTypeName = insertPromotionDTO.getPromotionType();
        //获取促销活动详情
        String promotionDetail = insertPromotionDTO.getPromotionDetail();

        //判断促销活动名称是否重复
        Example promotionExample = new Example(Promotion.class);
        promotionExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("promotionName", promotionName);
        List<Promotion> promotionList = promotionMapper.selectByExample(promotionExample);

        if(CollectionUtils.isNotEmpty(promotionList)){
            throw new CustomException("该促销活动名称已存在");
        }

        //获取促销活动类型ID
        Example promotionTypeExample = new Example(PromotionType.class);
        promotionTypeExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("promotionType", promotionTypeName);
        List<PromotionType> promotionTypeList = promotionTypeMapper.selectByExample(promotionTypeExample);
        PromotionType promotionType = promotionTypeList.get(0);
        Long ptid = promotionType.getPtid();

        //插入数据
        Promotion insertPromotion = new Promotion();
        insertPromotion.setPid(snowFlake.nextId());
        insertPromotion.setPromotionName(promotionName);
        insertPromotion.setPtid(ptid);
        insertPromotion.setPromotionDetail(promotionDetail);
        promotionMapper.insertSelective(insertPromotion);

        //返回VO
        PromotionInfoVO promotionInfoVO = new PromotionInfoVO();
        BeanUtils.copyProperties(insertPromotion,promotionInfoVO);

        return ResultUtil.getResultData(promotionInfoVO);
    }

    /**
     * 更新促销商品
     * @param updatePromotionDTO
     * @return
     */
    @Override
    @Transactional
    public ResultData<PromotionInfoVO> updatePromotion(UpdatePromotionDTO updatePromotionDTO) {

        //获取促销活动ID
        Long pid = updatePromotionDTO.getPid();
        //获取促销活动名称
        String promotionName = updatePromotionDTO.getPromotionName();
        //获取促销活动类型
        String promotionTypeName = updatePromotionDTO.getPromotionType();
        //获取促销活动详情
        String promotionDetail = updatePromotionDTO.getPromotionDetail();

        //获取促销活动类型ID
        Example promotionTypeExample = new Example(PromotionType.class);
        promotionTypeExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("promotionType", promotionTypeName);
        List<PromotionType> promotionTypeList = promotionTypeMapper.selectByExample(promotionTypeExample);
        if (CollectionUtils.isEmpty(promotionTypeList)) {
            throw new CustomException("促销活动类型不存在");
        }
        PromotionType promotionType = promotionTypeList.get(0);
        Long ptid = promotionType.getPtid();

        //判断促销活动名称是否重复
        Example promotionExample = new Example(Promotion.class);
        Example.Criteria criteria = promotionExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("promotionName", promotionName)
                .andEqualTo("promotionDetail",promotionDetail);
        List<Promotion> promotionList = promotionMapper.selectByExample(promotionExample);

        // 如果名称存在，再检查名称和 ptid 是否同时存在
        if (CollectionUtils.isNotEmpty(promotionList)) {
            criteria.andEqualTo("ptid", ptid);
            List<Promotion> promotionListCheck = promotionMapper.selectByExample(promotionExample);
            if (CollectionUtils.isNotEmpty(promotionListCheck)) {
                throw new CustomException("该促销活动名称已存在");
            }
        }

        //更改数据
        Promotion updatePromotion = new Promotion();
        updatePromotion.setPid(pid);  // Use the existing pid
        updatePromotion.setPromotionName(promotionName);
        updatePromotion.setPtid(ptid);
        updatePromotion.setPromotionDetail(promotionDetail);
        int updateCount = promotionMapper.updateByPrimaryKeySelective(updatePromotion);

        if (updateCount == 0) {
            throw new CustomException("更新失败，未找到对应记录");
        }

        //返回VO
        PromotionInfoVO promotionInfoVO = new PromotionInfoVO();
        BeanUtils.copyProperties(updatePromotion, promotionInfoVO);
        promotionInfoVO.setPromotionName(promotionName);
        promotionInfoVO.setPromotionType(promotionTypeName);
        promotionInfoVO.setPromotionDetail(promotionDetail);

        return ResultUtil.getResultData(promotionInfoVO);
    }

    /**
     * 删除促销活动信息
     * @param deletePromotionDTO
     * @return
     */
    @Override
    @Transactional
    public ResultData<PromotionInfoVO> deletePromotion(DeletePromotionDTO deletePromotionDTO) {
        //获取促销商品id
        Long pid = deletePromotionDTO.getPid();

        //获取数据
        Example promotionExample = new Example(Promotion.class);
        promotionExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("pid",pid);
        List<Promotion> promotionList = promotionMapper.selectByExample(promotionExample);
        Promotion promotion = promotionList.get(0);

        //删除数据
        promotion.setIsdelete(1);
        promotionMapper.updateByExampleSelective(promotion, promotionExample);

        //返回VO
        PromotionInfoVO promotionInfoVO = new PromotionInfoVO();
        BeanUtils.copyProperties(promotion, promotionInfoVO);
        return ResultUtil.getResultData(promotionInfoVO);
    }


    @Override
    public ResultBase updatePromotionImg(MultipartFile file) {
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

    @Override
    @Transactional
    public ResultBase updatePromotionImgGuid(UpdatePromotionImgGuidDTO updatePromotionImgGuidDTO) {
        String guid = updatePromotionImgGuidDTO.getGuid();
        Long pid = updatePromotionImgGuidDTO.getPid();

        Example promotionExample = new Example(Promotion.class);
        promotionExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("pid", pid);
        List<Promotion> promotions = promotionMapper.selectByExample(promotionExample);
        if (promotions.isEmpty()) {
            throw new CustomException("促销活动不存在");
        }
        Promotion promotion = promotions.get(0);

        promotion.setImgGuid(guid);
        promotionMapper.updateByExampleSelective(promotion, promotionExample);

        return ResultUtil.getResultBase();
    }

    private void savePic(InputStream inputStream, String fileName) {
        OutputStream os = null;
        try {
            String path = uploadPath; // 修改为你的上传路径
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
                if (os != null) {
                    os.close();
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Transactional
    public ResultData<PromotionInfoVO> changeState(UpdatePromotionDTO updatePromotionDTO) {
        //获取促销商品id
        Long pid = updatePromotionDTO.getPid();
        //获取状态
        Integer state = updatePromotionDTO.getState();

        //根据id查询促销商品信息
        Example promotionExample = new Example(Promotion.class);
        promotionExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("pid",pid);
        List<Promotion> promotions = promotionMapper.selectByExample(promotionExample);
        Promotion promotion = promotions.get(0);

        //更新促销商品信息
        promotion.setState(state);
        promotionMapper.updateByExampleSelective(promotion,promotionExample);


        //获取促销类型名称
        Long ptid = promotion.getPtid();
        Example promotionTypeExample = new Example(PromotionType.class);
        promotionTypeExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("ptid",ptid);
        List<PromotionType> promotionTypeList = promotionTypeMapper.selectByExample(promotionTypeExample);
        Map<Long, PromotionType> promotionTypeMap = promotionTypeList.stream().collect(Collectors.toMap(PromotionType::getPtid, Function.identity()));

        //更新汉服展示信息 插入数据库
        Hanfu hanfu = new Hanfu();
        hanfu.setHid(snowFlake.nextId());
        hanfu.setImgGuid(promotion.getImgGuid());
        hanfu.setHanfuName(promotion.getPromotionName());
        hanfu.setHanfuType(promotionTypeMap.get(ptid).getPromotionType());
        hanfu.setState(state);
        hanfu.setIspromotion(1);
        hanfu.setHanfuDetail(promotion.getPromotionDetail());
        hanfuMapper.insertSelective(hanfu);

        //返回VO
        PromotionInfoVO promotionInfoVO = new PromotionInfoVO();
        BeanUtils.copyProperties(promotion,promotionInfoVO);
        promotionInfoVO.setPid(promotion.getPid());
        return ResultUtil.getResultData(promotionInfoVO);
    }

    @Override
    public ResultQuery<PromotionInfoVO> selectAllPromotions(QueryPromotionDTO queryPromotionDTO) {

        //分页
        Integer pageNum = queryPromotionDTO.getPageNum();
        Integer pageSize = queryPromotionDTO.getPageSize();

        if(pageNum != null && pageSize != null){
            PageHelper.startPage(pageNum,pageSize);
        }

        //获取促销活动
        Example promotionExample = new Example(Promotion.class);
        promotionExample.createCriteria().andEqualTo("isdelete",0)
                .andLike("promotionName","%"+queryPromotionDTO.getPromotionName()+"%");
        List<Promotion> promotionList = promotionMapper.selectByExample(promotionExample);

        //获取促销活动类型
        Set<Long> ptidSet = promotionList.stream().map(Promotion::getPtid).collect(Collectors.toSet());
        Example promotionTypeExample = new Example(PromotionType.class);
        promotionTypeExample.createCriteria().andEqualTo("isdelete",0)
                .andIn("ptid",ptidSet);
        List<PromotionType> promotionTypeList = promotionTypeMapper.selectByExample(promotionTypeExample);
        //将促销活动类型放入map中
        Map<Long,PromotionType> promotionTypeMap = promotionTypeList.stream().collect(Collectors.toMap(PromotionType::getPtid, Function.identity()));

        //判断是否存在
        //若为空
        if(CollectionUtils.isEmpty(promotionList)){
            return ResultUtil.getResultQuery(new ArrayList<>(),0);
        }

        //分页
        int tatol;
        if(pageNum != null && pageSize != null){
            tatol = (int)new PageInfo<>(promotionList).getTotal();
        }else {
            tatol = promotionList.size();
        }

        //若不为空 返回VO
        List<PromotionInfoVO> promotionInfoVOList = new ArrayList<>();
        for (Promotion promotion : promotionList) {
            PromotionInfoVO promotionInfoVO = new PromotionInfoVO();
            BeanUtils.copyProperties(promotion,promotionInfoVO);
            promotionInfoVO.setPromotionType(promotionTypeMap.get(promotion.getPtid()).getPromotionType());
            promotionInfoVOList.add(promotionInfoVO);
        }
        return ResultUtil.getResultQuery(promotionInfoVOList,tatol);
    }

    /**
     * 新增促销类型
     * @param insertPromotionTypeDTO
     * @return
     */
    @Override
    @Transactional
    public ResultData<PromotionTypeInfoVO> insertPromotionType(InsertPromotionTypeDTO insertPromotionTypeDTO) {
        //获取促销活动类型
        String promotionType = insertPromotionTypeDTO.getPromotionType();

        //判断促销活动类型是否重复
        Example promotionTypeExample = new Example(PromotionType.class);
        promotionTypeExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("promotionType", promotionType);
        List<PromotionType> promotionTypeList = promotionTypeMapper.selectByExample(promotionTypeExample);
        //已存在
        if(CollectionUtils.isNotEmpty(promotionTypeList)){
            throw new CustomException("该促销活动类型已存在");
        }

        //不存在 插入数据
        PromotionType insertPromotionType = new PromotionType();
        insertPromotionType.setPtid(snowFlake.nextId());
        BeanUtils.copyProperties(insertPromotionTypeDTO,insertPromotionType);
        promotionTypeMapper.insertSelective(insertPromotionType);

        //返回VO
        PromotionTypeInfoVO promotionTypeInfoVO = new PromotionTypeInfoVO();
        BeanUtils.copyProperties(insertPromotionType,promotionTypeInfoVO);
        return ResultUtil.getResultData(promotionTypeInfoVO);
    }

    /**
     * 查询促销类型
     * @return
     */
    @Override
    public ResultQuery<PromotionTypeInfoVO> selectAllPromotionTypes() {
        Example ptomotionTypeExample = new Example(PromotionType.class);
        ptomotionTypeExample.createCriteria().andEqualTo("isdelete",0);
        List<PromotionType> promotionTypeList = promotionTypeMapper.selectByExample(ptomotionTypeExample);

        //若为空
        if(CollectionUtils.isEmpty(promotionTypeList)){
            return ResultUtil.getResultQuery(new ArrayList<>(),0);
        }

        //不为空，返回VO
        List<PromotionTypeInfoVO> promotionTypeInfoVOS = new ArrayList<>();
        for (PromotionType promotionType : promotionTypeList) {
            PromotionTypeInfoVO promotionTypeInfoVO = new PromotionTypeInfoVO();
            BeanUtils.copyProperties(promotionType,promotionTypeInfoVO);
            promotionTypeInfoVOS.add(promotionTypeInfoVO);
        }
        return ResultUtil.getResultQuery(promotionTypeInfoVOS,promotionTypeList.size());
    }


}
