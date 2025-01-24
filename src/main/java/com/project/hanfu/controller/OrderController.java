package com.project.hanfu.controller;

import com.project.hanfu.config.Constant;
import com.project.hanfu.config.HttpMsg;
import com.project.hanfu.mapper.OrderDao;
import com.project.hanfu.mapper.UserDao;
import com.project.hanfu.model.Order;
import com.project.hanfu.model.OrderVo;
import com.project.hanfu.menu.StatusCode;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.model.User;
import com.project.hanfu.service.OrderService;
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
    private UserDao userDao;

    @Resource
    private OrderDao orderDao;

    @RequestMapping("/queryByAccount")
    ResultBase queryByAccount(@RequestParam("account") String account) {
        ResultBase resultBase = new ResultBase();
        if (StringUtil.isEmpty(account)) {
            return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.INVALID_PARAM);
        }
        List<Order> orders = orderService.queryByAccount(account);
        return resultBase.setCode(StatusCode.SUCCESS).setData(orders);
    }

    @RequestMapping("/find")
    ResultBase find(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey, @RequestParam("account") String account) {
        ResultBase resultBase = new ResultBase();
        Map<String, Object> map = new HashMap<>();
        List<Order> orders = orderService.find(searchKey, account);
        if (orders == null) {
            return resultBase.setCode(StatusCode.SUCCESS);
        }
        List<Order> items = orders.size() >= page * Constant.PAGE_SIZE ?
                orders.subList((page - 1) * Constant.PAGE_SIZE, page * Constant.PAGE_SIZE)
                : orders.subList((page - 1) * Constant.PAGE_SIZE, orders.size());
        int len = orders.size() % Constant.PAGE_SIZE == 0 ? orders.size() / Constant.PAGE_SIZE
                : (orders.size() / Constant.PAGE_SIZE + 1);
        List<OrderVo> vos = new ArrayList<>();
        for (Order item : items) {
            User user = userDao.queryById(item.getUid());
            OrderVo vo = new OrderVo();
            vo.setAddress(user.getAddress()).setPhone(user.getPhoneNo()).setUsername(user.getName())
                    .setAmount(item.getAmount()).setFlower(item.getFlower()).setId(item.getId())
                    .setUid(item.getUid()).setOrder_guid(item.getOrder_guid()).setPrice(item.getPrice())
                    .setState(item.getState());
            vos.add(vo);
        }
        map.put("items", vos);
        map.put("len", len);
        return resultBase.setCode(StatusCode.SUCCESS).setData(map);
    }

    @RequestMapping("/findAll")
    ResultBase findAll(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey) {
        ResultBase resultBase = new ResultBase();
        Map<String, Object> map = new HashMap<>();
        List<Order> orders = orderService.findAll(searchKey);
        if (orders == null) {
            return resultBase.setCode(StatusCode.SUCCESS);
        }
        List<Order> items = orders.size() >= page * Constant.PAGE_SIZE ?
                orders.subList((page - 1) * Constant.PAGE_SIZE, page * Constant.PAGE_SIZE)
                : orders.subList((page - 1) * Constant.PAGE_SIZE, orders.size());
        int len = orders.size() % Constant.PAGE_SIZE == 0 ? orders.size() / Constant.PAGE_SIZE
                : (orders.size() / Constant.PAGE_SIZE + 1);
        List<OrderVo> vos = new ArrayList<>();
        for (Order item : items) {
            User user = userDao.queryById(item.getUid());
            OrderVo vo = new OrderVo();
            vo.setAddress(user.getAddress()).setPhone(user.getPhoneNo()).setUsername(user.getName())
                    .setAmount(item.getAmount()).setFlower(item.getFlower()).setId(item.getId())
                    .setUid(item.getUid()).setOrder_guid(item.getOrder_guid()).setPrice(item.getPrice())
                    .setState(item.getState());
            vos.add(vo);
        }
        map.put("items", vos);
        map.put("len", len);
        return resultBase.setCode(StatusCode.SUCCESS).setData(map);
    }

    @RequestMapping("/update")
    ResultBase update(@RequestBody Order order) {
        ResultBase resultBase = new ResultBase();
        int ans = orderService.update(order);
        if (ans >= 0) {
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.UPDATE_USER_OK);
        }
        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.UPDATE_USER_FAILED);
    }

    @RequestMapping("/changeState")
    ResultBase changeState(@RequestBody Order order) {
        orderDao.changeState(order);
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

