package com.project.hanfu.service;

import com.project.hanfu.model.Cart;
import com.project.hanfu.model.Orders;

import java.util.List;

/**
 * 订单 服务层
 *
 * @author: ShanZhu
 * @date: 2024-01-24
 */
public interface OrderService {

    int add(Cart cart);
    int delete(int uid);
    int update(Orders orders);
    List<Orders> find(String searchKey, String account);
    List<Orders> findAll(String searchKey);
    List<Orders> queryByAccount(String account);
}
