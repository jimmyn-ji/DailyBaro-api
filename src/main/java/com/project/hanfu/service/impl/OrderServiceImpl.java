package com.project.hanfu.service.impl;

import com.project.hanfu.exception.CustomException;
import com.project.hanfu.mapper.CartMapper;
import com.project.hanfu.mapper.OrdersMapper;
import com.project.hanfu.mapper.UserMapper;
import com.project.hanfu.model.Cart;
import com.project.hanfu.model.Orders;
import com.project.hanfu.model.User;
import com.project.hanfu.model.dto.*;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.OrderService;
import com.project.hanfu.util.CollectionUtils;
import com.project.hanfu.util.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    public ResultData<OrderInfoVO> deleteOrder(DeleteOrderDTO deleteOrderDTO) {
        //获取订单主键
        Long oid = deleteOrderDTO.getOid();

        //查询订单信息
        Example ordersExample = new Example(Orders.class);
        ordersExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("oid", oid);
        List<Orders> ordersList = ordersMapper.selectByExample(ordersExample);

        //判断订单是否存在
        if (CollectionUtils.isEmpty(ordersList)) {
            throw new CustomException("订单不存在");
        }

        Orders orders = ordersList.get(0);
        orders.setIsdelete(1);
        ordersMapper.updateByPrimaryKey(orders);

        //返回VO
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        BeanUtils.copyProperties(orders, orderInfoVO);
        return ResultUtil.getResultData(orderInfoVO);
    }

    /**
     * 根据用户账号查询订单信息
     *
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
        if (CollectionUtils.isEmpty(orders)) {
            return ResultUtil.getResultQuery(new ArrayList<>(), 0);
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
        return ResultUtil.getResultQuery(orderInfoVOS, orderInfoVOS.size());
    }


    /**
     * 客户分页查询订单信息
     *
     * @param queryOrderDTO
     * @return
     */
    @Override
    public ResultQuery<OrderInfoVO> queryOrderInfoByUser(QueryOrderDTO queryOrderDTO) {
        String account = queryOrderDTO.getAccount();
        String searchKey = queryOrderDTO.getSearchKey();

        //根据account查询uid
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete", 0)
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
        if (CollectionUtils.isEmpty(orders)) {
            return ResultUtil.getResultQuery(new ArrayList<>(), 0);
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
            orderInfoVO.setReview(order.getReview());
            orderInfoVO.setHid(order.getHid());
            orderInfoVO.setCid(order.getCid());
            orderInfoVO.setUserName(user.getName());
            orderInfoVO.setAddress(user.getAddress());
            orderInfoVO.setPhoneNo(user.getPhoneNo());
            orderInfoVO.setIsreview(order.getIsreview());
            //订单状态 0未发货 1已发货
            orderInfoVO.setState(order.getState());
            orderInfoVOS.add(orderInfoVO);
        }
        return ResultUtil.getResultQuery(orderInfoVOS, orderInfoVOS.size());
    }

    /**
     * 管理员分页查询订单信息
     *
     * @param queryOrderDTO
     * @return
     */
    @Override
    public ResultQuery<OrderInfoVO> queryOrderInfoByAdmin(QueryOrderDTO queryOrderDTO) {
        //获取模糊查询条件 用于查询汉服名称
        String searchKey = queryOrderDTO.getSearchKey();

        Example orderExample = new Example(Orders.class);
        orderExample.createCriteria().andEqualTo("isdelete", 0)
                .andLike("hanfuName", "%" + searchKey + "%");
        List<Orders> orders = ordersMapper.selectByExample(orderExample);

        //为空
        if (CollectionUtils.isEmpty(orders)) {
            return ResultUtil.getResultQuery(new ArrayList<>(), 0);
        }

        //不为空 获取所有uids 并获取 去重后的信息
        Set<Long> uidSet = orders.stream().map(Orders::getUid).collect(Collectors.toSet());

        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete", 0)
                .andIn("uid", uidSet);
        List<User> users = userMapper.selectByExample(userExample);
        //获取user name，phone_no,address信息
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getUid, user -> user));

        List<OrderInfoVO> orderInfoVOS = new ArrayList<>();
        for (Orders order : orders) {
            OrderInfoVO orderInfoVO = new OrderInfoVO();
            orderInfoVO.setOid(order.getOid());
            orderInfoVO.setUid(order.getUid());
            orderInfoVO.setHanfuName(order.getHanfuName());
            orderInfoVO.setHanfuQty(order.getHanfuQty());
            orderInfoVO.setPrice(order.getPrice());
            orderInfoVO.setPhoneNo(userMap.get(order.getUid()).getPhoneNo());
            orderInfoVO.setAddress(userMap.get(order.getUid()).getAddress());
            orderInfoVO.setUserName(userMap.get(order.getUid()).getName());
            orderInfoVO.setState(order.getState());
            orderInfoVO.setReview(order.getReview());
            orderInfoVO.setIsreview(order.getIsreview());
            orderInfoVOS.add(orderInfoVO);

        }
        return ResultUtil.getResultQuery(orderInfoVOS, orderInfoVOS.size());
    }

    /**
     * 更新订单状态 0未发货 1已发货
     *
     * @param updateOrderInfoDTO
     * @return
     */
    @Override
    public ResultData<OrderInfoVO> updateOrderState(UpdateOrderInfoDTO updateOrderInfoDTO) {
        //获取订单id
        Long oid = updateOrderInfoDTO.getOid();
        //获取订单状态
        Integer state = updateOrderInfoDTO.getState();

        //查询订单表
        Example orderExample = new Example(Orders.class);
        orderExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("oid", oid);
        List<Orders> orders = ordersMapper.selectByExample(orderExample);
        Orders updateOrders = orders.get(0);

        //更新订单状态
        updateOrders.setState(state);
        ordersMapper.updateByExampleSelective(updateOrders, orderExample);

        //返回VO
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        BeanUtils.copyProperties(updateOrders, orderInfoVO);
        return ResultUtil.getResultData(orderInfoVO);
    }

    @Override
    public ResultData<OrderInfoVO> createReview(InsertReviewDTO insertReviewDTO) {
        Long uid = insertReviewDTO.getUid();
        Long oid = insertReviewDTO.getOid();
        String review = insertReviewDTO.getReview();
        Integer state = insertReviewDTO.getState();


        //查询订单表
        Example orderExample = new Example(Orders.class);
        orderExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("oid", oid);
        List<Orders> orders = ordersMapper.selectByExample(orderExample);
        Orders insertOrders = orders.get(0);

        //判断是否已发货
        if(insertOrders.getState().equals(0)){
            throw new CustomException("该订单还未发货，不能评价");
        }
        //更新评价内容
        insertOrders.setReview(review);
        insertOrders.setIsreview(1);
        ordersMapper.updateByExampleSelective(insertOrders, orderExample);

        OrderInfoVO orderInfoVO= new OrderInfoVO();
        BeanUtils.copyProperties(insertOrders, orderInfoVO);
        return ResultUtil.getResultData(orderInfoVO);
    }

    /**
     * 结算购物车信息
     * @param accountDTO
     * @return
     */
//    @Override
//    @Transactional
//    public ResultData<OrderInfoVO> checkOut(AccountDTO accountDTO) {
//        //获取用户账号
//        String account = accountDTO.getAccount();
//
//        //根据账号查询用户信息
//        Example userExample = new Example(User.class);
//        userExample.createCriteria().andEqualTo("isdelete", 0)
//                .andEqualTo("account", account)
//                .andEqualTo("role", "user");
//        List<User> users = userMapper.selectByExample(userExample);
//
//        //获取uid
//        User user = users.get(0);
//        Long uid = user.getUid();
//        //根据用户id查询购物车信息
//        Example cartExample = new Example(Cart.class);
//        cartExample.createCriteria().andEqualTo("isdelete", 0)
//                .andEqualTo("uid", uid);
//        List<Cart> carts = cartMapper.selectByExample(cartExample);
//
//        OrderInfoVO orderInfoVO = new OrderInfoVO();
//
//        for (Cart cart : carts) {
//            //生成订单 将购物车信息插入订单表
//            Orders insertOrders = new Orders();
//            insertOrders.setOid(snowFlake.nextId());
//            insertOrders.setHanfuName(cart.getHanfuName());
//            insertOrders.setHanfuQty(cart.getHanfuQty());
//            insertOrders.setPrice(cart.getPrice());
//            insertOrders.setUid(cart.getUid());
//            //设置订单状态 0未发货 1已发货
//            insertOrders.setState(0);
//            ordersMapper.insertSelective(insertOrders);
//
//            //删除购物车信息
//            cart.setIsdelete(1);
//            cartMapper.updateByExampleSelective(cart, cartExample);
//
//            //返回VO
//            BeanUtils.copyProperties(insertOrders, orderInfoVO);
//        }
//        return ResultUtil.getResultData(orderInfoVO);
//    }

    /**
     * 创建订单
     * @param createOrderDTO
     * @return
     */
    @Override
    @Transactional
    public ResultData<OrderInfoVO> createOrder(CreateOrderDTO createOrderDTO) {
        String account = createOrderDTO.getAccount();
        Double totalAmount = createOrderDTO.getTotalAmount();
        List<InsertOrderInfoDTO> items = createOrderDTO.getItems();

        // 根据账号获取用户
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("account", account);
        User user = userMapper.selectByExample(userExample).get(0);

        // 清空购物车
        Example cartExample = new Example(Cart.class);
        cartExample.createCriteria().andEqualTo("uid", user.getUid())
                .andEqualTo("isdelete", 0);

        Orders order = new Orders();
        Cart cart = new Cart();
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        // 循环生成订单
        for (InsertOrderInfoDTO item : items) {
            order.setOid(snowFlake.nextId());
            order.setHanfuName(item.getHanfuName());
            order.setHanfuQty(item.getHanfuQty());
            order.setPrice(item.getPrice());
            order.setUid(user.getUid());
            order.setCid(item.getCid());
            order.setHid(item.getHid());
            order.setState(0); // 设置订单状态
            cart.setCid(item.getCid());
            cart.setIsdelete(1);
            cart.setHid(item.getHid());
            BeanUtils.copyProperties(cart, cartExample);
            ordersMapper.insertSelective(order);
            cartMapper.updateByExampleSelective(cart, cartExample);
        }

        //返回VO
        BeanUtils.copyProperties(order, orderInfoVO);
        return ResultUtil.getResultData(orderInfoVO);

    }

}
