package com.project.hanfu.controller;

import com.project.hanfu.model.dto.*;
import com.project.hanfu.model.vo.OrderInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 订单控制层
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单/结算
     * @param createOrderDTO
     * @return
     */
    @RequestMapping("/create")
    public ResultData<OrderInfoVO> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        return orderService.createOrder(createOrderDTO);
    }

    /**
     * 删除订单
     * @param oid
     * @return
     */
    @RequestMapping("/delete")
    public ResultData<OrderInfoVO> deleteOrder(@RequestParam("oid") Long oid) {
        DeleteOrderDTO deleteOrderDTO=new DeleteOrderDTO();
        deleteOrderDTO.setOid(oid);
        return orderService.deleteOrder(deleteOrderDTO);
    }

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

    /**
     * 修改订单状态 0未发货 1已发货
     * @param updateOrderInfoDTO
     * @return
     */
    @RequestMapping("/changeState")
    ResultData<OrderInfoVO> updateOrderState(@RequestBody UpdateOrderInfoDTO updateOrderInfoDTO) {
        return orderService.updateOrderState(updateOrderInfoDTO);
    }

    /**
     * 创建评价信息
     * @param insertReviewDTO
     * @return
     */
    @RequestMapping("/review")
    public ResultData<OrderInfoVO> createReview(@RequestBody InsertReviewDTO insertReviewDTO) {
        return orderService.createReview(insertReviewDTO);
    }


}

