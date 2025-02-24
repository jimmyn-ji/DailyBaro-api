package com.project.transactions.service.impl;

import com.project.transactions.mapper.OrderMapper;
import com.project.transactions.mapper.ProductMapper;
import com.project.transactions.mapper.UserMapper;
import com.project.transactions.mapper.ProductTypeMapper;
import com.project.transactions.model.dto.InsertOrderDTO;
import com.project.transactions.model.dto.OidDTO;
import com.project.transactions.model.dto.QueryInfoDTO;
import com.project.transactions.model.vo.OrderInfoVO;
import com.project.transactions.model.Order;
import com.project.transactions.model.Product;
import com.project.transactions.model.User;
import com.project.transactions.model.ProductType;
import com.project.transactions.util.SnowFlake;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import com.project.transactions.result.ResultData;
import com.project.transactions.result.ResultUtil;
import org.springframework.beans.BeanUtils;
import com.project.transactions.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private SnowFlake snowflake;

    /**
     * 创建订单
     * @param insertOrderDTO
     * @return
     */
    @Override
    public ResultData<OrderInfoVO> createOrder(InsertOrderDTO insertOrderDTO) {
        //获取产品
        Long pid=insertOrderDTO.getPid();

        //根据id获取产品信息
        Product product=productMapper.selectByPrimaryKey(pid);
        //获取用户信息
        Long uid=product.getUid();
        User user=userMapper.selectByPrimaryKey(uid);

        //获取分类信息
        Long ptId=product.getPtId();
        ProductType productType=productTypeMapper.selectByPrimaryKey(ptId);
        //插入数据
        Order insertOrder=new Order();
        insertOrder.setOid(snowflake.nextId());
        insertOrder.setPid(pid);
        insertOrder.setUid(uid);
        insertOrder.setOrderNo(insertOrder.getOid().toString());
        insertOrder.setPrice(product.getPrice());
        insertOrder.setTotalAmount(product.getPrice());
        insertOrder.setPaymentMethod(insertOrderDTO.getPaymentMethod());
        insertOrder.setStatus(1);
        orderMapper.insertSelective(insertOrder);

        //返回结果
        OrderInfoVO orderInfoVO=new OrderInfoVO();
        BeanUtils.copyProperties(insertOrder, orderInfoVO);
        orderInfoVO.setProductName(productType.getProductTypeName());
        orderInfoVO.setPtId(product.getPtId());
        orderInfoVO.setProductTypeName(productType.getProductTypeName());
        if(StringUtil.isNotEmpty(insertOrderDTO.getAddress())){
            orderInfoVO.setAddress(insertOrderDTO.getAddress());
            user.setAddress(insertOrderDTO.getAddress());
            userMapper.updateByPrimaryKeySelective(user);
        }
        return ResultUtil.getResultData(orderInfoVO);
    }

    /**
     * 查询订单列表
     * @param queryInfoDTO
     * @return
     */
    @Override
    public ResultData<List<OrderInfoVO>> selectOrderList(QueryInfoDTO queryInfoDTO) {
        //获取用户id
        Long uid=queryInfoDTO.getUid();

        //获取订单列表
        Example orderExample=new Example(Order.class);
        orderExample.createCriteria().andEqualTo("uid", uid);
        List<Order> orderList=orderMapper.selectByExample(orderExample);
        //订单Map
        Map<Long,Order> orderMap=new HashMap<>();
        for(Order order:orderList){
            orderMap.put(order.getPid(), order);
        }

        //获取产品id列表
        List<Long> pidList=orderList.stream().map(Order::getPid).collect(Collectors.toList());
        //获取产品信息
        Example productExample=new Example(Product.class);
        productExample.createCriteria().andIn("pid", pidList);
        List<Product> productList=productMapper.selectByExample(productExample);
        //产品Map
        Map<Long,Product> productMap=new HashMap<>();
        for(Product product:productList){
            productMap.put(product.getPid(), product);
        }

        //返回结果
        List<OrderInfoVO> orderInfoVOList=new ArrayList<>();
        for(Order order:orderList){
            OrderInfoVO orderInfoVO=new OrderInfoVO();
            BeanUtils.copyProperties(order, orderInfoVO);
            orderInfoVO.setProductName(productMap.get(order.getPid()).getProductName());
            orderInfoVO.setProductTypeName(productMap.get(order.getPid()).getProductTypeName());
            orderInfoVO.setPtId(productMap.get(order.getPid()).getPtId());
            orderInfoVOList.add(orderInfoVO);
        }
        return ResultUtil.getResultData(orderInfoVOList);
    }

    /**
     * 确认/取消订单
     * @param oidDTO
     * @return
     */
    @Override
    public ResultData<OrderInfoVO> confirmOrder(OidDTO oidDTO) {
        //获取订单id
        Long oid=oidDTO.getOid();
        //获取订单信息
        Order order=orderMapper.selectByPrimaryKey(oid);
        //更新订单状态 0-已取消,1-待支付,2-已支付,3-已发货,4-已完成
        if(oidDTO.getStatus()==0){
            order.setStatus(0);
        }else if(oidDTO.getStatus()==2){
            order.setStatus(2);
        }
        orderMapper.updateByPrimaryKeySelective(order);
        //返回结果
        OrderInfoVO orderInfoVO=new OrderInfoVO();
        BeanUtils.copyProperties(order, orderInfoVO);
        return ResultUtil.getResultData(orderInfoVO);
    }

    /**
     * 删除订单
     * @param oidDTO
     * @return
     */
    @Override
    public ResultData<OrderInfoVO> deleteOrder(OidDTO oidDTO) {
        //获取订单id
        Long oid=oidDTO.getOid();
        //获取订单信息
        Order order=orderMapper.selectByPrimaryKey(oid);
        //更新订单状态 0-已取消,1-待支付,2-已支付,3-已发货,4-已完成
        order.setStatus(0);
        orderMapper.updateByPrimaryKeySelective(order);
        //返回结果
        OrderInfoVO orderInfoVO=new OrderInfoVO();
        BeanUtils.copyProperties(order, orderInfoVO);
        return ResultUtil.getResultData(orderInfoVO);
    }

    /**
     * 发货
     * @param oidDTO
     * @return
     */
    @Override
    public ResultData<OrderInfoVO> sendOrder(OidDTO oidDTO) {
        //获取订单id
        Long oid=oidDTO.getOid();
        //获取订单信息
        Order order=orderMapper.selectByPrimaryKey(oid);
        //更新订单状态 0-已取消,1-待支付,2-已支付,3-已发货,4-已完成
        order.setStatus(3);
        orderMapper.updateByPrimaryKeySelective(order);
        //返回结果
        OrderInfoVO orderInfoVO=new OrderInfoVO();
        BeanUtils.copyProperties(order, orderInfoVO);
        return ResultUtil.getResultData(orderInfoVO);
    }

    /**
     * 确认收货
     * @param oidDTO
     * @return
     */ 
    @Override
    public ResultData<OrderInfoVO> confirmReceiveOrder(OidDTO oidDTO) {
        //获取订单id
        Long oid=oidDTO.getOid();
        //获取订单信息
        Order order=orderMapper.selectByPrimaryKey(oid);
        //更新订单状态 0-已取消,1-待支付,2-已支付,3-已发货,4-已完成
        order.setStatus(4);
        orderMapper.updateByPrimaryKeySelective(order);
        //返回结果
        OrderInfoVO orderInfoVO=new OrderInfoVO();
        BeanUtils.copyProperties(order, orderInfoVO);
        return ResultUtil.getResultData(orderInfoVO);
    }
}

