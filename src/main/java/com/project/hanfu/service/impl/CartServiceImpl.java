package com.project.hanfu.service.impl;

import com.project.hanfu.mapper.CartDao;
import com.project.hanfu.mapper.CartMapper;
import com.project.hanfu.mapper.UserDao;
import com.project.hanfu.mapper.UserMapper;
import com.project.hanfu.model.Cart;
import com.project.hanfu.model.User;
import com.project.hanfu.model.dto.InsertCartInfoDTO;
import com.project.hanfu.model.vo.CartInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.CartService;
import com.project.hanfu.util.CollectionUtils;
import com.project.hanfu.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartDao cartDao;

    @Resource
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private SnowFlake snowFlake;

    @Override
    @Transactional
    public ResultData<CartInfoVO> insertCartInfo(InsertCartInfoDTO insertCartInfoDTO) {
//        获取用户账号
        String account = insertCartInfoDTO.getAccount();
//        根据账号获取用户id
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("account",account)
                .andEqualTo("role","user");
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        Long uid = user.getUid();
        insertCartInfoDTO.setUid(uid);

//        判断该商品是否已添加至购物车
        Example cartExample = new Example(Cart.class);
        cartExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("uid",uid)
                .andEqualTo("hid",insertCartInfoDTO.getHid());
        List<Cart> carts = cartMapper.selectByExample(cartExample);

        Cart cart=new Cart();
        if(CollectionUtils.isEmpty(carts)){
            //若不存在，插入商品信息
            cart.setCid(snowFlake.nextId());
            cart.setHid(insertCartInfoDTO.getHid());
            cart.setHanfuName(insertCartInfoDTO.getHanfuName());
            cart.setHanfuQty(BigDecimal.ONE);
            cart.setPrice(insertCartInfoDTO.getPrice());
            cart.setUid(uid);
            cartMapper.insertSelective(cart);
        }else{
            //若存在，更新对应商品数量信息
            cart.setCid(carts.get(0).getCid());
            cart.setHid(insertCartInfoDTO.getHid());
            cart.setHanfuName(insertCartInfoDTO.getHanfuName());
            cart.setHanfuQty(carts.get(0).getHanfuQty().add(BigDecimal.ONE));
            cart.setPrice(insertCartInfoDTO.getPrice());
            cart.setUid(uid);
            cartMapper.updateByExampleSelective(cart,cartExample);
        }

        CartInfoVO cartInfoVO=new CartInfoVO();
        cartInfoVO.setCid(cart.getCid());
        cartInfoVO.setHanfuName(cart.getHanfuName());
        cartInfoVO.setHanfuQty(cart.getHanfuQty());
        cartInfoVO.setPrice(cart.getPrice());
        cartInfoVO.setUid(cart.getUid());
        cartInfoVO.setHid(cart.getHid());
        return ResultUtil.getResultData(cartInfoVO);
    }



    @Override
    public int delete(int uid) {
        return cartDao.delete(uid);
    }

    @Override
    public int update(Cart cart) {
        return cartDao.update(cart);
    }

    @Override
    public List<Cart> find(String searchKey,String account) {
        return cartDao.find(searchKey,account);
    }

    @Override
    public List<Cart> queryByAccount(String account) {
        Integer uid = userDao.queryIdByAccount(account);
        return cartDao.queryByUid(uid);
    }


}
