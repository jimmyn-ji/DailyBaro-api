package com.project.hanfu.service.impl;

import com.project.hanfu.mapper.OrderDao;
import com.project.hanfu.mapper.UserDao;
import com.project.hanfu.model.Cart;
import com.project.hanfu.model.Orders;
import com.project.hanfu.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderdao;

    @Resource
    private UserDao userDao;

    @Override
    public int add(Cart cart) {
        return orderdao.add(cart);
    }

    @Override
    public int delete(int uid) {
        return orderdao.delete(uid);
    }

    @Override
    public int update(Orders orders) {
        return orderdao.update(orders);
    }

    @Override
    public List<Orders> find(String searchKey, String account) {
        Integer uid = userDao.queryIdByAccount(account);
        return orderdao.find(searchKey,uid);
    }

    @Override
    public List<Orders> findAll(String searchKey) {
        return orderdao.findAll(searchKey);
    }

    @Override
    public List<Orders> queryByAccount(String account) {
        Integer uid = userDao.queryIdByAccount(account);
        return orderdao.queryByUid(uid);
    }


}
