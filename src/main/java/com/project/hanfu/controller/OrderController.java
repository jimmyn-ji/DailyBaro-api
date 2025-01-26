package com.project.hanfu.controller;

import com.project.hanfu.config.Constant;
import com.project.hanfu.config.HttpMsg;
import com.project.hanfu.mapper.OrderDao;
import com.project.hanfu.mapper.UserDao;
import com.project.hanfu.model.Orders;
import com.project.hanfu.model.OrdersVo;
import com.project.hanfu.menu.StatusCode;
import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.HanfuQueryDTO;
import com.project.hanfu.model.dto.OrderQueryDTO;
import com.project.hanfu.model.vo.CartInfoVO;
import com.project.hanfu.model.vo.HanfuInfoVO;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 订单 控制层
 *
 * @author: ShanZhu
 * @date: 2024-01-24
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderDao orderDao;


    /**
     * 根据用户账号查询订单信息接口
     * @param account
     * @return
     */
    @RequestMapping("/queryByAccount")
    ResultQuery<OrderInfoVO> queryInfoByAccount(@RequestParam("account") String account) {
        //        从 URL 查询字符串中接收数据并转化为 JSON
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount(account);
        return orderService.queryInfoByAccount(accountDTO);
    }

    /**
     * 分页查询订单信息
     * @param page
     * @param searchKey
     * @param account
     * @return
     */
    @RequestMapping("/find")
    ResultQuery<OrderInfoVO> queryOrderInfo(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey, @RequestParam("account") String account) {
        //从 URL 查询字符串中接收数据并转化为 JSON
        OrderQueryDTO orderQueryDTO =new OrderQueryDTO();
        orderQueryDTO.setPage(page);
        orderQueryDTO.setSearchKey(searchKey);
        orderQueryDTO.setAccount(account);
        return orderService.queryOrderInfo(orderQueryDTO);
    }


    @RequestMapping("/findAll")
    ResultBase findAll(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey) {
        ResultBase resultBase = new ResultBase();
        Map<String, Object> map = new HashMap<>();
        List<Orders> orders = orderService.findAll(searchKey);
        if (orders == null) {
            return resultBase.setCode(StatusCode.SUCCESS);
        }
        List<Orders> items = orders.size() >= page * Constant.PAGE_SIZE ?
                orders.subList((page - 1) * Constant.PAGE_SIZE, page * Constant.PAGE_SIZE)
                : orders.subList((page - 1) * Constant.PAGE_SIZE, orders.size());
        int len = orders.size() % Constant.PAGE_SIZE == 0 ? orders.size() / Constant.PAGE_SIZE
                : (orders.size() / Constant.PAGE_SIZE + 1);
        List<OrdersVo> vos = new ArrayList<>();
//        for (Order item : items) {
//            User user = userDao.queryById(item.getUid());
//            OrderVo vo = new OrderVo();
//            vo.setAddress(user.getAddress()).setPhone(user.getPhoneNo()).setUsername(user.getName())
//                    .setAmount(item.getAmount()).setFlower(item.getFlower()).setId(item.getId())
//                    .setUid(item.getUid()).setOrder_guid(item.getOrder_guid()).setPrice(item.getPrice())
//                    .setState(item.getState());
//            vos.add(vo);
//        }
        map.put("items", vos);
        map.put("len", len);
        return resultBase.setCode(StatusCode.SUCCESS).setData(map);
    }

    @RequestMapping("/update")
    ResultBase update(@RequestBody Orders orders) {
        ResultBase resultBase = new ResultBase();
        int ans = orderService.update(orders);
        if (ans >= 0) {
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.UPDATE_USER_OK);
        }
        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.UPDATE_USER_FAILED);
    }

    @RequestMapping("/changeState")
    ResultBase changeState(@RequestBody Orders orders) {
        orderDao.changeState(orders);
        return new ResultBase().setCode(StatusCode.SUCCESS).setMessage(HttpMsg.UPDATE_ORDER_OK);
    }

    @DeleteMapping("/delete")
    ResultBase delete(@RequestParam("id") int id) {
        ResultBase resultBase = new ResultBase();
        int ans = orderService.delete(id);
        if (ans == 1) {
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.DELETE_USER_OK);
        }
        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.DELETE_USER_FAILED);
    }

}

