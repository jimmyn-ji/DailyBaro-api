package com.project.transactions.service;

import com.project.transactions.model.dto.OidDTO;
import com.project.transactions.model.dto.QueryInfoDTO;
import com.project.transactions.model.vo.OrderInfoVO;
import com.project.transactions.result.ResultData;
import com.project.transactions.model.dto.InsertOrderDTO;

import java.util.List;

public interface OrderService {

    // 创建订单
    public ResultData<OrderInfoVO> createOrder(InsertOrderDTO insertOrderDTO);

    // 查询订单
    public ResultData<List<OrderInfoVO>> selectOrderList(QueryInfoDTO queryInfoDTO);

     // 支付/取消订单
    public ResultData<OrderInfoVO> confirmOrder(OidDTO oidDTO);

    // 删除订单
    public ResultData<OrderInfoVO> deleteOrder(OidDTO oidDTO);


    // 发货
    public ResultData<OrderInfoVO> sendOrder(OidDTO oidDTO);

    // 确认收货
    public ResultData<OrderInfoVO> confirmReceiveOrder(OidDTO oidDTO);
    
}
