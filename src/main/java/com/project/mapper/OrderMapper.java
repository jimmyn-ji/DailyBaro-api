package com.project.mapper;

import com.project.model.Order;
import com.project.model.vo.OrderOneVO;
import com.project.model.vo.OrderWithProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    // 创建订单
    int createOrder(Order order);

    // 根据ID获取订单
    Order getOrderById(Long orderId);

    OrderOneVO getOrderByOid(Long orderId);
    // 更新订单信息
    int updateOrder(Order order);

    List<OrderWithProduct> getOrdersByUserId(Long userId);

    List<OrderWithProduct> selectAllOrdersWithProducts();
}