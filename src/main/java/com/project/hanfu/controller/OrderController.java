package com.project.hanfu.controller;

import com.project.hanfu.config.Constant;
import com.project.hanfu.config.HttpMsg;
import com.project.hanfu.mapper.OrderDao;
import com.project.hanfu.model.Orders;
import com.project.hanfu.model.OrdersVo;
import com.project.hanfu.menu.StatusCode;
import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.QueryOrderDTO;
import com.project.hanfu.model.dto.UpdateOrderInfoDTO;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.OrderService;
import org.springframework.web.bind.annotation.*;

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
     * 客户分页查询订单信息
     * @param page
     * @param searchKey
     * @param account
     * @return
     */
    @RequestMapping("/find")
    ResultQuery<OrderInfoVO> queryOrderInfoByUser(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey, @RequestParam("account") String account) {
        //从 URL 查询字符串中接收数据并转化为 JSON
        QueryOrderDTO queryOrderDTO =new QueryOrderDTO();
        queryOrderDTO.setPage(page);
        queryOrderDTO.setSearchKey(searchKey);
        queryOrderDTO.setAccount(account);
        return orderService.queryOrderInfoByUser(queryOrderDTO);
    }


    /**
     * 管理员分页查询订单信息
     * @param page
     * @param searchKey
     * @return
     */
    @RequestMapping("/findAll")
    ResultQuery<OrderInfoVO> queryOrderInfoByAdmin(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey){
        //从 URL 查询字符串中接收数据并转化为 JSON
        QueryOrderDTO queryOrderDTO =new QueryOrderDTO();
        queryOrderDTO.setPage(page);
        queryOrderDTO.setSearchKey(searchKey);
        return orderService.queryOrderInfoByAdmin(queryOrderDTO);
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
    ResultData<OrderInfoVO> updateOrderState(@RequestBody UpdateOrderInfoDTO updateOrderInfoDTO) {
        return orderService.updateOrderState(updateOrderInfoDTO);
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

