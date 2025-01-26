package com.project.hanfu.service;

import com.project.hanfu.model.Cart;
import com.project.hanfu.model.Orders;
import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.QueryOrderDTO;
import com.project.hanfu.model.dto.UpdateOrderInfoDTO;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import org.springframework.web.bind.annotation.RequestBody;

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
    ResultData<OrderInfoVO> updateOrderState(@RequestBody UpdateOrderInfoDTO updateOrderInfoDTO);

    int add(Cart cart);
    int delete(int uid);
    int update(Orders orders);
    List<Orders> find(String searchKey, String account);
    List<Orders> findAll(String searchKey);
    List<Orders> queryByAccount(String account);
}
