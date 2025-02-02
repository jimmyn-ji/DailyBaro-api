package com.project.hanfu.service.impl;

import com.project.hanfu.exception.CustomException;
import com.project.hanfu.mapper.HanfuMapper;
import com.project.hanfu.mapper.OrdersMapper;
import com.project.hanfu.mapper.UserMapper;
import com.project.hanfu.model.Hanfu;
import com.project.hanfu.model.Orders;
import com.project.hanfu.model.User;
import com.project.hanfu.model.dto.*;
import com.project.hanfu.model.vo.HanfuDetailVO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.model.vo.HanfuTypeVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.HanfuService;
import com.project.hanfu.util.CollectionUtils;
import com.project.hanfu.util.SnowFlake;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HanfuServiceImpl implements HanfuService {

    @Value("${uploadPath}")
    private String uploadPath;

    @Autowired
    private HanfuMapper hanfuMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SnowFlake snowFlake;



    @Override
    @Transactional
    public ResultData<HanfuInfoVO> insertHanfuInfo(InsertHanfuInfoDTO insertHanfuInfoDTO) {
        String hanfuName = insertHanfuInfoDTO.getHanfuName();
        String hanfuType = insertHanfuInfoDTO.getHanfuType();
        BigDecimal price = insertHanfuInfoDTO.getPrice();
        String hanfuDetail = insertHanfuInfoDTO.getHanfuDetail();

        Example hanfuExample = new Example(Hanfu.class);
        hanfuExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("hanfuName",hanfuName);
        List<Hanfu> hanfuList = hanfuMapper.selectByExample(hanfuExample);
        //查重
        if(CollectionUtils.isNotEmpty(hanfuList)){
            throw new CustomException("汉服名称重复");
        }

        //插入数据
        Hanfu hanfu = new Hanfu();
        hanfu.setHid(snowFlake.nextId());
        BeanUtils.copyProperties(insertHanfuInfoDTO,hanfu);
        hanfuMapper.insertSelective(hanfu);

        HanfuInfoVO hanfuInfoVO = new HanfuInfoVO();
        BeanUtils.copyProperties(insertHanfuInfoDTO,hanfuInfoVO);

        return ResultUtil.getResultData(hanfuInfoVO);
    }

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
     * 获取商品评价信息
     * @param queryHanfuDetailDTO
     * @return
     */
    @Override
    public ResultData<HanfuDetailVO> selectProductDetail(QueryHanfuDetailDTO queryHanfuDetailDTO) {
        //获取汉服id
        Long hid = queryHanfuDetailDTO.getHid();

        //根据id查询汉服信息
        Example hanfuExample = new Example(Hanfu.class);
        hanfuExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("hid",hid);
        List<Hanfu> hanfuList = hanfuMapper.selectByExample(hanfuExample);
        if(CollectionUtils.isEmpty(hanfuList)){
            throw new CustomException("未找到对应汉服信息");
        }

        Hanfu hanfu = hanfuList.get(0);
        //查询订单评价信息
        Example ordersExample = new Example(Orders.class);
        ordersExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("hid",hid);
        List<Orders> ordersList = ordersMapper.selectByExample(ordersExample);

        HanfuDetailVO hanfuDetailVO = new HanfuDetailVO();
        BeanUtils.copyProperties(hanfu,hanfuDetailVO);
        //若无订单信息：无评价内容
        if(CollectionUtils.isEmpty(ordersList)){
            return ResultUtil.getResultData(hanfuDetailVO);
        }

        //提取uidSet
        Set<Long> uidSet = ordersList.stream().map(Orders::getUid).collect(Collectors.toSet());

        //获取用户姓名
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete",0)
                .andIn("uid",uidSet);
        List<User> userList = userMapper.selectByExample(userExample);

        //将用户信息存入Map
        Map<Long, String> userMap = userList.stream().collect(Collectors.toMap(User::getUid, User::getName));

        //组装评价信息
        List<String> reviews = new ArrayList<>();
        for (Orders orders : ordersList) {
            String review = orders.getReview();
            Long uid = orders.getUid();

            //获取用户名
            String userName = userMap.get(uid);
            if(StringUtil.isNotEmpty(review)){
                reviews.add(userName + ":" + review);// 格式：用户名: 评价
            }
        }
        //返回汉服信息
        hanfuDetailVO.setReviews(reviews);
        hanfuDetailVO.setImgGuid(hanfu.getImgGuid());
        return ResultUtil.getResultData(hanfuDetailVO);

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
        hanfu.setOriginalPrice(updateHanfuInfoDTO.getOriginalPrice());
        hanfuMapper.updateByExampleSelective(hanfu,hanfuExample);

        //返回VO
        HanfuInfoVO hanfuInfoVO =new HanfuInfoVO();
        BeanUtils.copyProperties(hanfu,hanfuInfoVO);

        if(StringUtil.isNotEmpty(String.valueOf(updateHanfuInfoDTO.getOriginalPrice()))){
            hanfuInfoVO.setOriginalPrice(updateHanfuInfoDTO.getOriginalPrice());
        }
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
