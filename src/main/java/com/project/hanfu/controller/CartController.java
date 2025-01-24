package com.project.hanfu.controller;

import com.project.hanfu.config.Constant;
import com.project.hanfu.config.HttpMsg;
import com.project.hanfu.mapper.FlowersDao;
import com.project.hanfu.model.Cart;
import com.project.hanfu.menu.StatusCode;
import com.project.hanfu.model.dto.InsertCartInfoDTO;
import com.project.hanfu.model.dto.InsertUserDTO;
import com.project.hanfu.model.vo.CartInfoVO;
import com.project.hanfu.model.vo.UserInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.service.CartService;
import com.project.hanfu.service.OrderService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    private FlowersDao flowersDao;

    /**
     * 查询用户购物车
     *
     * @param account 用户账号
     * @return 购物车
     */
    @RequestMapping("/queryByAccount")
    ResultBase queryByAccount(@RequestParam("account") String account) {
        ResultBase resultBase = new ResultBase();

        if (StringUtil.isEmpty(account)) {
            return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.INVALID_PARAM);
        }

        List<Cart> carts = cartService.queryByAccount(account);

        for (Cart cart : carts) {
            float price = flowersDao.queryPrice(cart.getFid());
            cart.setPrice(BigDecimal.valueOf(price));
        }

        return resultBase.setCode(StatusCode.SUCCESS).setData(carts);
    }


    /**
     * 分页查询购物车
     *
     * @param page      页数
     * @param searchKey 查询条件
     * @param account   账户
     * @return 购物车列表
     */
    @RequestMapping("/find")
    ResultBase find(
            @RequestParam("page") int page,
            @RequestParam("searchKey") String searchKey,
            @RequestParam("account") String account
    ) {

        ResultBase resultBase = new ResultBase();

        Map<String, Object> map = new HashMap<>();
        List<Cart> carts = cartService.find(searchKey, account);

        if (carts == null) {
            return resultBase.setCode(StatusCode.SUCCESS);
        }

        List<Cart> items = carts.size() >= page * Constant.PAGE_SIZE ?
                carts.subList((page - 1) * Constant.PAGE_SIZE, page * Constant.PAGE_SIZE)
                : carts.subList((page - 1) * Constant.PAGE_SIZE, carts.size());

        int len = carts.size() % Constant.PAGE_SIZE == 0 ? carts.size() / Constant.PAGE_SIZE
                : (carts.size() / Constant.PAGE_SIZE + 1);
        map.put("items", items);
        map.put("len", len);

        return resultBase.setCode(StatusCode.SUCCESS).setData(map);
    }

    /**
     * 购买
     *
     * @param account 账号
     * @return 结果
     */
    @RequestMapping("/buy")
    ResultBase buy(@RequestParam("account") String account) {
        ResultBase resultBase = new ResultBase();

        // 查该用户的购物车
        List<Cart> carts = (List<Cart>) queryByAccount(account).getData();
        for (Cart cart : carts) {
            // 增加订单数据
            orderService.add(cart);
            // 删除购物车数据
            cartService.delete(cart.getId());
        }

        return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.BUY_OK);
    }



    @RequestMapping("/create")
    ResultData<CartInfoVO> insertCartInfo(@RequestBody InsertCartInfoDTO insertCartInfoDTO) {
        return cartService.insertCartInfo(insertCartInfoDTO);
    }

    /**
     * 更新购物车
     *
     * @param cart 购物车信息
     * @return 结果
     */
    @RequestMapping("/update")
    ResultBase update(@RequestBody Cart cart) {
        ResultBase resultBase = new ResultBase();

        int ans = cartService.update(cart);

        if (ans >= 0) {
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.UPDATE_USER_OK);
        }

        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.UPDATE_USER_FAILED);
    }

    /**
     * 删除购物车
     *
     * @param id 购物车id
     * @return 结果
     */
    @DeleteMapping("/delete")
    ResultBase delete(@RequestParam("id") int id) {
        ResultBase resultBase = new ResultBase();
        int ans = cartService.delete(id);
        if (ans == 1) {
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.DELETE_USER_OK);
        }
        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.DELETE_USER_FAILED);
    }

}

