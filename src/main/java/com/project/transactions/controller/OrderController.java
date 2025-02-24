package com.project.transactions.controller;

import com.project.transactions.service.OrderService;
import com.project.transactions.result.ResultData;
import com.project.transactions.result.ResultUtil;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.project.transactions.model.vo.OrderInfoVO;
import com.project.transactions.model.dto.QueryInfoDTO;
import com.project.transactions.model.dto.InsertOrderDTO;
import com.project.transactions.model.dto.OidDTO;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 创建订单
    @RequestMapping("/createOrder")
    public ResultData<OrderInfoVO> createOrder(@RequestBody InsertOrderDTO insertOrderDTO) {
        return orderService.createOrder(insertOrderDTO);
    }

    // 查询订单
    @RequestMapping("/selectOrderList")
    public ResultData<List<OrderInfoVO>> selectOrderList(@RequestBody QueryInfoDTO queryInfoDTO) {
        return orderService.selectOrderList(queryInfoDTO);
    }

    // 支付/取消订单
    @RequestMapping("/confirmOrder")
    public ResultData<OrderInfoVO> confirmOrder(@RequestBody OidDTO oidDTO) {
        return orderService.confirmOrder(oidDTO);
    }

    // 删除订单
    @RequestMapping("/deleteOrder")
    public ResultData<OrderInfoVO> deleteOrder(@RequestBody OidDTO oidDTO) {
        return orderService.deleteOrder(oidDTO);
    }

    // 发货
    @RequestMapping("/sendOrder")
    public ResultData<OrderInfoVO> sendOrder(@RequestBody OidDTO oidDTO) {
        return orderService.sendOrder(oidDTO);
    }
    
    // 确认收货
    @RequestMapping("/confirmReceiveOrder")
    public ResultData<OrderInfoVO> confirmReceiveOrder(@RequestBody OidDTO oidDTO) {
        return orderService.confirmReceiveOrder(oidDTO);
    }
}

