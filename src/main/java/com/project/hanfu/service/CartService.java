package com.project.hanfu.service;

import com.project.hanfu.model.Cart;
import com.project.hanfu.model.dto.*;
import com.project.hanfu.model.vo.CartInfoVO;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

/**
 * 购物车 服务
 *
 * @author: ShanZhu
 * @date: 2024-01-24
 */
public interface CartService {

    /**
     * 添加购物车
     * @param insertCartInfoDTO
     * @return
     */
    ResultData<CartInfoVO> insertCartInfo(InsertCartInfoDTO insertCartInfoDTO);

    /**
     * 查询用户账号下购物车信息
     * @param accountDTO
     * @return
     */
    ResultQuery<CartInfoVO> queryInfoByAccount(AccountDTO accountDTO);

    /**
     * 删除购物车信息
     * @param cidDTO
     * @return
     */
    ResultData<CartInfoVO> deleteCartInfo(CidDTO cidDTO);

    /**
     * 结算购物车信息
     * @param accountDTO
     * @return
     */
    ResultData<OrderInfoVO> checkOut(AccountDTO accountDTO);

    /**
     * 更新购物车信息
     * @param updateCartInfoDTO
     * @return
     */
    ResultData<CartInfoVO> updateCartInfo(UpdateCartInfoDTO updateCartInfoDTO);

}
