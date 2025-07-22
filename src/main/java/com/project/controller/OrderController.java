package com.project.controller;

import com.github.pagehelper.PageInfo;
import com.project.model.dto.CreateOrderDTO;
import com.project.model.vo.OrderListVO;
import com.project.model.vo.OrderOneVO;
import com.project.model.vo.OrderVO;
import com.project.service.OrderService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/createOrder")
    public Result<OrderVO> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        return orderService.createOrder(createOrderDTO);
    }

    @RequestMapping("/getOrderById/{orderId}")
    public Result<OrderOneVO> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @RequestMapping("/payOrder/{orderId}")
    public Result<Void> payOrder(@PathVariable Long orderId) {
        return orderService.payOrder(orderId);
    }

    @RequestMapping("/cancelOrder/{orderId}")
    public Result<Void> cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @RequestMapping("/getOrdersByUserId/{userId}")
    public Result<List<OrderListVO>> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }


    @GetMapping("/selectOrderListByAdmin")
    public Result<PageInfo<OrderListVO>> selectOrderListByAdmin(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return orderService.selectOrderListByAdmin(pageNum, pageSize);
    }
}