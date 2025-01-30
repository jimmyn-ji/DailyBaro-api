package com.project.hanfu.service;

import com.project.hanfu.model.dto.*;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 订单业务层接口
 */
public interface OrderService {

    /**
     * 创建订单信息/结算
     * @param createOrderDTO
     * @return
     */
    ResultData<OrderInfoVO> createOrder(CreateOrderDTO createOrderDTO);

    /**
     * 删除订单信息
     * @param deleteOrderDTO
     * @return
     */
    ResultData<OrderInfoVO> deleteOrder(DeleteOrderDTO deleteOrderDTO);

    /**
     * 根据账号查询订单信息
     * @param accountDTO
     * @return
     */
    ResultQuery<OrderInfoVO> queryInfoByAccount(AccountDTO accountDTO);

    /**
     * 客户分页查询订单信息
     * @param queryOrderDTO
     * @return
     */
    ResultQuery<OrderInfoVO> queryOrderInfoByUser(QueryOrderDTO queryOrderDTO);


    /**
     *  管理员分页查询订单信息
     * @param queryOrderDTO
     * @return
     */
    ResultQuery<OrderInfoVO> queryOrderInfoByAdmin(QueryOrderDTO queryOrderDTO);


    /**
     * 管理员修改订单状态 0未发货 1已发货
     * @param updateOrderInfoDTO
     * @return
     */
    ResultData<OrderInfoVO> updateOrderState(UpdateOrderInfoDTO updateOrderInfoDTO);


    /**
     * 添加评价
     * @param insertReviewDTO
     * @return
     */
    ResultData<OrderInfoVO> createReview(InsertReviewDTO insertReviewDTO);

    /**
     * 结算购物车信息
     * @param accountDTO
     * @return
     */
//    ResultData<OrderInfoVO> checkOut(AccountDTO accountDTO);




}
