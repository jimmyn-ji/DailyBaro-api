package com.project.service;

import com.github.pagehelper.PageInfo;
import com.project.model.dto.CreateOrderDTO;
import com.project.model.vo.OrderListVO;
import com.project.model.vo.OrderOneVO;
import com.project.model.vo.OrderVO;
import com.project.util.Result;

import java.util.List;

public interface OrderService {
    Result<OrderVO> createOrder(CreateOrderDTO createOrderDTO);

    Result<OrderOneVO> getOrderById(Long orderId);

    Result<Void> payOrder(Long orderId);

    Result<Void> cancelOrder(Long orderId);

    Result<List<OrderListVO>> getOrdersByUserId(Long userId);

    Result<PageInfo<OrderListVO>> selectOrderListByAdmin(Integer pageNum, Integer pageSize);
}