package com.project.hanfu.mapper;

import com.project.hanfu.model.Orders;
import com.project.hanfu.model.vo.OrderInfoVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrdersMapper extends Mapper<Orders> {

    //    管理员查询订单信息
    List<OrderInfoVO> selectOrdersByUids(@Param("uids") List<Long> uids);
}
