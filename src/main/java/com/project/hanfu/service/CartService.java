package com.project.hanfu.service;

import com.project.hanfu.model.Cart;
import com.project.hanfu.model.dto.InsertCartInfoDTO;
import com.project.hanfu.model.vo.CartInfoVO;
import com.project.hanfu.result.ResultData;

import java.util.List;

/**
 * 购物车 服务
 *
 * @author: ShanZhu
 * @date: 2024-01-24
 */
public interface CartService {

    ResultData<CartInfoVO> insertCartInfo(InsertCartInfoDTO insertCartInfoDTO);

    int delete(int uid);
    int update(Cart cart);
    List<Cart> find(String searchKey,String account);
    List<Cart> queryByAccount(String account);
}
