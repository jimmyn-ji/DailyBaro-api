package com.project.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.mapper.OrderMapper;
import com.project.mapper.ShoppingCartMapper;
import com.project.model.Order;
import com.project.model.ShoppingCartItem;
import com.project.model.dto.CreateOrderDTO;
import com.project.model.vo.*;
import com.project.service.OrderService;
import com.project.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public Result<OrderVO> createOrder(CreateOrderDTO createOrderDTO) {
        System.out.println("接收到的 createOrderDTO: " + createOrderDTO);


        Order order = new Order();
        // 确保所有必要字段都被正确复制
        order.setUserId(createOrderDTO.getUserId());
        order.setOrderAmount(BigDecimal.valueOf(createOrderDTO.getOrderAmount()));
        order.setOrderStatus(createOrderDTO.getOrderStatus() != null ? createOrderDTO.getOrderStatus() : "待支付");
        order.setCreateTime(new Date());
        order.setPayTime(createOrderDTO.getPayTime());
        order.setIsPaid(0);
        order.setIsCancelled(0);

        System.out.println("转换后的 Order 对象: " + order);

        orderMapper.createOrder(order);
        Long newOrderId = order.getOrderId();

        if (createOrderDTO.getSelectedItemIds() != null && !createOrderDTO.getSelectedItemIds().isEmpty()) {
            List<ShoppingCartItem> cartItemsToUpdate = new ArrayList<>();
            for (Long itemId : createOrderDTO.getSelectedItemIds()) {
                ShoppingCartItem cartItem = new ShoppingCartItem();
                cartItem.setItemId(itemId);
                cartItem.setOrderId(newOrderId);
                cartItem.setIsdeleted(1);
                cartItemsToUpdate.add(cartItem);
                shoppingCartMapper.updateCartItem(cartItem);
            }
        }

        return Result.success(convertToVO(order));
    }

    @Override
    public Result<OrderOneVO> getOrderById(Long orderId) {
        OrderOneVO order = orderMapper.getOrderByOid(orderId);
        if (order == null) {
            return Result.fail("未找到该订单");
        }
        return Result.success(order);
    }

    @Override
    public Result<Void> payOrder(Long orderId) {
        Order order = orderMapper.getOrderById(orderId);
        if (order == null) {
            return Result.fail("未找到该订单");
        }
        // 模拟支付逻辑
        order.setIsPaid(1);
        order.setPayTime(new Date());
        orderMapper.updateOrder(order);
        return Result.success();
    }

    @Override
    public Result<Void> cancelOrder(Long orderId) {
        Order order = orderMapper.getOrderById(orderId);
        if (order == null) {
            return Result.fail("未找到该订单");
        }
        // 模拟取消订单逻辑
        order.setIsCancelled(1);
        orderMapper.updateOrder(order);
        return Result.success();
    }

    @Override
    public Result<List<OrderListVO>> getOrdersByUserId(Long userId) {
        // 1. 查询订单及关联商品信息
        List<OrderWithProduct> orderProducts = orderMapper.getOrdersByUserId(userId);

        // 2. 按订单ID分组
        Map<Long, List<OrderWithProduct>> orderMap = orderProducts.stream()
                .collect(Collectors.groupingBy(OrderWithProduct::getOrderId));

        // 3. 转换为OrderVO列表
        List<OrderListVO> orderVOs = orderMap.values().stream()
                .map(this::convertToOrderVO)
                .collect(Collectors.toList());

        return Result.success(orderVOs);
    }

    @Override
    public Result<PageInfo<OrderListVO>> selectOrderListByAdmin(Integer pageNum, Integer pageSize) {
        try {
            // 1. 设置分页参数
            PageHelper.startPage(pageNum, pageSize);

            // 2. 查询所有订单及关联商品信息
            List<OrderWithProduct> orderProducts = orderMapper.selectAllOrdersWithProducts();

            // 3. 按订单ID分组
            Map<Long, List<OrderWithProduct>> orderMap = orderProducts.stream()
                    .collect(Collectors.groupingBy(OrderWithProduct::getOrderId));

            // 4. 转换为OrderListVO列表
            List<OrderListVO> orderVOs = orderMap.values().stream()
                    .map(this::convertToOrderVO)
                    .collect(Collectors.toList());

            // 5. 获取分页信息
            PageInfo<OrderListVO> pageInfo = new PageInfo<>(orderVOs);

            return Result.success(pageInfo);
        } catch (Exception e) {
            log.error("管理员查询订单列表失败", e);
            return Result.fail("查询订单列表失败");
        }
    }

    // 转换方法 - 将同一订单的商品列表转换为OrderVO
    private OrderListVO convertToOrderVO(List<OrderWithProduct> orderProducts) {
        if (orderProducts == null || orderProducts.isEmpty()) {
            return null;
        }

        // 获取订单基本信息(取第一条记录)
        OrderWithProduct first = orderProducts.get(0);
        OrderListVO orderVO = new OrderListVO();

        // 设置订单基本信息
        orderVO.setOrderId(first.getOrderId());
        orderVO.setUserId(first.getUserId());
        orderVO.setOrderAmount(first.getOrderAmount());
        orderVO.setOrderStatus(first.getOrderStatus());
        orderVO.setCreateTime(first.getCreateTime());
        orderVO.setPayTime(first.getPayTime());
        orderVO.setPaid(first.getIsPaid());
        orderVO.setCancelled(first.getIsCancelled());

        // 转换商品信息
        List<ProductListVO> productVOs = orderProducts.stream()
                .map(this::convertToProductVO)
                .collect(Collectors.toList());

        orderVO.setProducts(productVOs);

        return orderVO;
    }

    // 转换方法 - 单个商品信息转换
    private ProductListVO convertToProductVO(OrderWithProduct orderProduct) {
        ProductListVO productVO = new ProductListVO();
        productVO.setProductId(orderProduct.getProductId());
        productVO.setName(orderProduct.getProductName());
        productVO.setDescription(orderProduct.getProductDescription());
        productVO.setPrice(orderProduct.getProductPrice());
        productVO.setImageUrl(orderProduct.getProductImageUrl());
        productVO.setStock(orderProduct.getProductStock());
        return productVO;
    }

    private OrderVO convertToVO(Order order) {
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);

        // BigDecimal 转 Double，防止 null 报错
        vo.setOrderAmount(order.getOrderAmount() != null ? order.getOrderAmount().doubleValue() : 0.0);

        // 不转换成 boolean，保持 Integer 类型
        vo.setIsPaid(order.getIsPaid());
        vo.setIsCancelled(order.getIsCancelled());

        return vo;
    }
}