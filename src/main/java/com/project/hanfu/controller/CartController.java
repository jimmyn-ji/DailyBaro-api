package com.project.hanfu.controller;

import com.project.hanfu.config.Constant;
import com.project.hanfu.config.HttpMsg;
import com.project.hanfu.model.Cart;
import com.project.hanfu.menu.StatusCode;
import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.CidDTO;
import com.project.hanfu.model.dto.InsertCartInfoDTO;
import com.project.hanfu.model.vo.CartInfoVO;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.CartService;
import com.project.hanfu.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 购物车 controller
 */
@RestController
@RequestMapping("cart")
public class CartController {

    @Resource
    private CartService cartService;

    @Resource
    private OrderService orderService;


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
    ResultData<CartInfoVO> deleteCartInfo(@RequestParam("id") Long cid) {
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


    /**
     * 分页查询购物车
     *
     * @param page      页数
     * @param searchKey 查询条件
     * @param account   账户
     * @return 购物车列表
     */
//    @RequestMapping("/find")
//    ResultBase find(
//            @RequestParam("page") int page,
//            @RequestParam("searchKey") String searchKey,
//            @RequestParam("account") String account
//    ) {
//
//        ResultBase resultBase = new ResultBase();
//
//        Map<String, Object> map = new HashMap<>();
//        List<Cart> carts = cartService.find(searchKey, account);
//
//        if (carts == null) {
//            return resultBase.setCode(StatusCode.SUCCESS);
//        }
//
//        List<Cart> items = carts.size() >= page * Constant.PAGE_SIZE ?
//                carts.subList((page - 1) * Constant.PAGE_SIZE, page * Constant.PAGE_SIZE)
//                : carts.subList((page - 1) * Constant.PAGE_SIZE, carts.size());
//
//        int len = carts.size() % Constant.PAGE_SIZE == 0 ? carts.size() / Constant.PAGE_SIZE
//                : (carts.size() / Constant.PAGE_SIZE + 1);
//        map.put("items", items);
//        map.put("len", len);
//
//        return resultBase.setCode(StatusCode.SUCCESS).setData(map);
//    }






    /**
     * 更新购物车
     *
     * @param cart 购物车信息
     * @return 结果
     */
//    @RequestMapping("/update")
//    ResultBase update(@RequestBody Cart cart) {
//        ResultBase resultBase = new ResultBase();
//
//        int ans = cartService.update(cart);
//
//        if (ans >= 0) {
//            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.UPDATE_USER_OK);
//        }
//
//        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.UPDATE_USER_FAILED);
//    }



}

