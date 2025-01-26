package com.project.hanfu.service;

import com.project.hanfu.model.Cart;
import com.project.hanfu.model.Orders;
import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.OrderQueryDTO;
import com.project.hanfu.model.vo.CartInfoVO;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultQuery;

import java.util.List;

/**
 * 订单 服务层
 *
 * @author: ShanZhu
 * @date: 2024-01-24
 */
public interface OrderService {

    /**
     * 根据账号查询订单信息
     * @param accountDTO
     * @return
     */
    ResultQuery<OrderInfoVO> queryInfoByAccount(AccountDTO accountDTO);

    /**
     * 分页查询订单信息
     * @param orderQueryDTO
     * @return
     */
    ResultQuery<OrderInfoVO> queryOrderInfo(OrderQueryDTO orderQueryDTO);

    int add(Cart cart);
    int delete(int uid);
    int update(Orders orders);
    List<Orders> find(String searchKey, String account);
    List<Orders> findAll(String searchKey);
    List<Orders> queryByAccount(String account);
}
