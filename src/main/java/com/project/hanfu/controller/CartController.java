package com.project.hanfu.controller;

import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.CidDTO;
import com.project.hanfu.model.dto.InsertCartInfoDTO;
import com.project.hanfu.model.vo.CartInfoVO;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.CartService;
import com.project.hanfu.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 购物车 controller
 */
@RestController
@RequestMapping("cart")
public class CartController {

    @Resource
    private CartService cartService;


    /**
     * 添加购物车
     * @param insertCartInfoDTO
     * @return
     */
    @RequestMapping("/create")
    ResultData<CartInfoVO> insertCartInfo(@RequestBody InsertCartInfoDTO insertCartInfoDTO) {
        return cartService.insertCartInfo(insertCartInfoDTO);
    }

    /**
     * 删除购物车信息
     * @param cid
     * @return
     */
    @RequestMapping("/delete")
    ResultData<CartInfoVO> deleteCartInfo(@RequestParam("cid") Long cid) {
        System.out.println("接收到的 cid: " + cid);  // 调试输出
//        从 URL 查询字符串中接收数据并转化为 JSON
        CidDTO cidDTO= new CidDTO();
        cidDTO.setCid(cid);
        return cartService.deleteCartInfo(cidDTO);
    }

    /**
     * 根据用户账号查询用户购物车
     *
     * @param account 用户账号
     * @return 购物车
     */
    @RequestMapping("/queryByAccount")
    ResultQuery<CartInfoVO> queryInfoByAccount(@RequestParam("account") String account) {
//        从 URL 查询字符串中接收数据并转化为 JSON
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount(account);
        return cartService.queryInfoByAccount(accountDTO);
    }

    /**
     * 购物车结算功能
     * @param account
     * @return
     */
    @RequestMapping("/buy")
    ResultData<OrderInfoVO> checkOut(@RequestParam("account") String account){
        // 从 URL 查询字符串中接收数据并转化为 JSON
        AccountDTO accountDTO=new AccountDTO();
        accountDTO.setAccount(account);
        return cartService.checkOut(accountDTO);
    }



}

