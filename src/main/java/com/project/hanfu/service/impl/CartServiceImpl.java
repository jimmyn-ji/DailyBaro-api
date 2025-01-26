package com.project.hanfu.service.impl;

import com.project.hanfu.exception.CustomException;
import com.project.hanfu.mapper.*;
import com.project.hanfu.model.Cart;
import com.project.hanfu.model.Orders;
import com.project.hanfu.model.User;
import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.CidDTO;
import com.project.hanfu.model.dto.InsertCartInfoDTO;
import com.project.hanfu.model.vo.CartInfoVO;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.CartService;
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

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    @Transactional
    public ResultData<CartInfoVO> insertCartInfo(InsertCartInfoDTO insertCartInfoDTO) {
//        获取用户账号
        String account = insertCartInfoDTO.getAccount();
//        根据账号获取用户id
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("account", account)
                .andEqualTo("role", "user");
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        Long uid = user.getUid();
        insertCartInfoDTO.setUid(uid);

//        判断该商品是否已添加至购物车
        Example cartExample = new Example(Cart.class);
        cartExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("uid", uid)
                .andEqualTo("hid", insertCartInfoDTO.getHid());
        List<Cart> carts = cartMapper.selectByExample(cartExample);

        Cart cart = new Cart();
        if (CollectionUtils.isEmpty(carts)) {
            //若不存在，插入商品信息
            cart.setCid(snowFlake.nextId());
            cart.setHid(insertCartInfoDTO.getHid());
            cart.setHanfuName(insertCartInfoDTO.getHanfuName());
            cart.setHanfuQty(BigDecimal.ONE);
            cart.setPrice(insertCartInfoDTO.getPrice());
            cart.setUid(uid);
            cartMapper.insertSelective(cart);
        } else {
            //若存在，更新对应商品数量信息
            cart.setCid(carts.get(0).getCid());
            cart.setHid(insertCartInfoDTO.getHid());
            cart.setHanfuName(insertCartInfoDTO.getHanfuName());
            cart.setHanfuQty(carts.get(0).getHanfuQty().add(BigDecimal.ONE));
            cart.setPrice(insertCartInfoDTO.getPrice());
            cart.setUid(uid);
            cartMapper.updateByExampleSelective(cart, cartExample);
        }

        CartInfoVO cartInfoVO = new CartInfoVO();
        cartInfoVO.setCid(cart.getCid());
        cartInfoVO.setHanfuName(cart.getHanfuName());
        cartInfoVO.setHanfuQty(cart.getHanfuQty());
        cartInfoVO.setPrice(cart.getPrice());
        cartInfoVO.setUid(cart.getUid());
        cartInfoVO.setHid(cart.getHid());
        return ResultUtil.getResultData(cartInfoVO);
    }

    /**
     * 查询用户购物车信息
     *
     * @param accountDTO
     * @return
     */
    @Override
    public ResultQuery<CartInfoVO> queryInfoByAccount(AccountDTO accountDTO) {
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

        //根据uid查询购物车信息
        Example cartExample = new Example(Cart.class);
        cartExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("uid", uid);
        List<Cart> carts = cartMapper.selectByExample(cartExample);

        // 为空
        if (CollectionUtils.isEmpty(carts)) {
            return ResultUtil.getResultQuery(new ArrayList<>(), 0);
        }

        // 不为空
        List<CartInfoVO> cartInfoVOS = new ArrayList<>();
        for (Cart cart : carts) {
            CartInfoVO cartInfoVO = new CartInfoVO();
            cartInfoVO.setHid(cart.getHid());
            cartInfoVO.setHanfuName(cart.getHanfuName());
            cartInfoVO.setHanfuQty(cart.getHanfuQty());
            cartInfoVO.setPrice(cart.getPrice());
            cartInfoVO.setUid(cart.getUid());
            cartInfoVO.setCid(cart.getCid());
            cartInfoVOS.add(cartInfoVO);
        }

        return ResultUtil.getResultQuery(cartInfoVOS, cartInfoVOS.size());
    }

    /**
     * 删除购物车信息
     * @param cidDTO
     * @return
     */
    @Override
    @Transactional
    public ResultData<CartInfoVO> deleteCartInfo(CidDTO cidDTO) {
        Long cid = cidDTO.getCid();

        Example cartExample = new Example(Cart.class);
        cartExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("cid", cid);
        List<Cart> carts = cartMapper.selectByExample(cartExample);

        Cart cart = carts.get(0);
        cart.setIsdelete(1);
        cartMapper.updateByExampleSelective(cart, cartExample);

        CartInfoVO cartInfoVO = new CartInfoVO();
        BeanUtils.copyProperties(cart, cartInfoVO);

        return ResultUtil.getResultData(cartInfoVO);
    }

    @Override
    @Transactional
    public ResultData<OrderInfoVO> checkOut(AccountDTO accountDTO) {
        //获取用户账号
        String account = accountDTO.getAccount();

        //根据账号查询用户信息
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("account", account)
                .andEqualTo("role","user");
        List<User> users = userMapper.selectByExample(userExample);

        //获取uid
        User user = users.get(0);
        Long uid = user.getUid();
        //根据用户id查询购物车信息
        Example cartExample = new Example(Cart.class);
        cartExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("uid", uid);
        List<Cart> carts = cartMapper.selectByExample(cartExample);

        OrderInfoVO orderInfoVO = new OrderInfoVO();

        for (Cart cart : carts) {
            //生成订单 将购物车信息插入订单表
            Orders insertOrders = new Orders();
            insertOrders.setOid(snowFlake.nextId());
            insertOrders.setHanfuName(cart.getHanfuName());
            insertOrders.setHanfuQty(cart.getHanfuQty());
            insertOrders.setPrice(cart.getPrice());
            insertOrders.setUid(cart.getUid());
            //设置订单状态 0未发货 1已发货
            insertOrders.setState(0);
            ordersMapper.insertSelective(insertOrders);

            //删除购物车信息
            cart.setIsdelete(1);
            cartMapper.updateByExampleSelective(cart, cartExample);

            //返回VO
            BeanUtils.copyProperties(insertOrders, orderInfoVO);
        }
        return ResultUtil.getResultData(orderInfoVO);
    }

}

