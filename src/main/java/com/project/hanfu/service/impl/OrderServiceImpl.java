package com.project.hanfu.service.impl;

import com.project.hanfu.exception.CustomException;
import com.project.hanfu.mapper.OrderDao;
import com.project.hanfu.mapper.OrdersMapper;
import com.project.hanfu.mapper.UserDao;
import com.project.hanfu.mapper.UserMapper;
import com.project.hanfu.model.Cart;
import com.project.hanfu.model.Orders;
import com.project.hanfu.model.User;
import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.OrderQueryDTO;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.OrderService;
import com.project.hanfu.util.CollectionUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderdao;

    @Resource
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 根据用户账号查询订单信息
     * @param accountDTO
     * @return
     */
    @Override
    public ResultQuery<OrderInfoVO> queryInfoByAccount(AccountDTO accountDTO) {
        String account = accountDTO.getAccount();
        if (StringUtil.isEmpty(account)) {
            throw new CustomException("请重新登录");
        }

        //根据account查询uid
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete")
                .andEqualTo("account", account)
                .andEqualTo("role", "user");
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        Long uid = user.getUid();

        //根据uid查询订单信息
        Example orderExample = new Example(Orders.class);
        orderExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("uid", uid);
        List<Orders> orders = ordersMapper.selectByExample(orderExample);

        //为空
        if(CollectionUtils.isEmpty(orders)){
            return ResultUtil.getResultQuery(new ArrayList<>(),0);
        }

        //不为空
        List<OrderInfoVO> orderInfoVOS = new ArrayList<>();
        for (Orders order : orders) {
            OrderInfoVO orderInfoVO = new OrderInfoVO();
            orderInfoVO.setOid(order.getOid());
            orderInfoVO.setUid(order.getUid());
            orderInfoVO.setHanfuName(order.getHanfuName());
            orderInfoVO.setOrderGuid(order.getOrderGuid());
            orderInfoVO.setPrice(order.getPrice());
            orderInfoVO.setHanfuQty(order.getHanfuQty());
            orderInfoVOS.add(orderInfoVO);
        }
        return ResultUtil.getResultQuery(orderInfoVOS,orderInfoVOS.size());
    }

    /**
     * 分页查询订单信息
     * @param orderQueryDTO
     * @return
     */
    @Override
    public ResultQuery<OrderInfoVO> queryOrderInfo(OrderQueryDTO orderQueryDTO) {
        String account = orderQueryDTO.getAccount();
        String searchKey = orderQueryDTO.getSearchKey();

        //根据account查询uid
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("account", account)
                .andEqualTo("role", "user");
        List<User> users = userMapper.selectByExample(userExample);

        User user = users.get(0);
        Long uid = user.getUid();

        Example orderExample = new Example(Orders.class);
        orderExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("uid", uid)
                .andLike("hanfuName", "%" + searchKey + "%");

        List<Orders> orders = ordersMapper.selectByExample(orderExample);

        //为空
        if(CollectionUtils.isEmpty(orders)){
            return ResultUtil.getResultQuery(new ArrayList<>(),0);
        }

        //不为空
        List<OrderInfoVO> orderInfoVOS = new ArrayList<>();
        for (Orders order : orders) {
            OrderInfoVO orderInfoVO = new OrderInfoVO();
            orderInfoVO.setOid(order.getOid());
            orderInfoVO.setUid(order.getUid());
            orderInfoVO.setHanfuName(order.getHanfuName());
            orderInfoVO.setOrderGuid(order.getOrderGuid());
            orderInfoVO.setPrice(order.getPrice());
            orderInfoVO.setHanfuQty(order.getHanfuQty());
            //订单状态 0未发货 1已发货
            orderInfoVO.setState(order.getState());
            orderInfoVOS.add(orderInfoVO);
        }
        return ResultUtil.getResultQuery(orderInfoVOS,orderInfoVOS.size());
    }

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
