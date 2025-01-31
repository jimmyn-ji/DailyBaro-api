package com.project.hanfu.service.impl;

import com.project.hanfu.mapper.HanfuMapper;
import com.project.hanfu.mapper.HanfuTypeMapper;
import com.project.hanfu.mapper.OrdersMapper;
import com.project.hanfu.model.Hanfu;
import com.project.hanfu.model.HanfuType;
import com.project.hanfu.model.Orders;
import com.project.hanfu.model.dto.QueryReportDTO;
import com.project.hanfu.model.vo.ReportByHanfuTypeInfoVO;
import com.project.hanfu.model.vo.ReportInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.ReportService;
import com.project.hanfu.util.CollectionUtils;
import com.project.hanfu.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private HanfuMapper hanfuMapper;

    @Autowired
    private HanfuTypeMapper hanfuTypeMapper;
    
    /**
     * 查询营业额信息
     * @param queryReportDTO
     * @return
     */
    @Override
    public ResultData<ReportInfoVO> selectSalesInfo(QueryReportDTO queryReportDTO) {

        // TODO: 根据条件查询订单表，计算日销售额、周销售额、月销售额
        Example ordersExample = new Example(Orders.class);
        ordersExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("state",1)
                .andBetween("createTime",queryReportDTO.getStartTime(),queryReportDTO.getEndTime());
        List<Orders> ordersList = ordersMapper.selectByExample(ordersExample);

        //日销售额
        BigDecimal dailySales = BigDecimal.ZERO;
        //周销售额
        BigDecimal weeklySales = BigDecimal.ZERO;
        //月销售额
        BigDecimal monthlySales = BigDecimal.ZERO;

        //获取当前时间
        Date currentDate = new Date();
        LocalDate currentLocalDate = DateUtil.dateToLocalDate(currentDate);

        // 获取当前日期的周数
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1); // 以周一为一周的起始
        int currentWeek = currentLocalDate.get(weekFields.weekOfYear());
        int currentYear = currentLocalDate.getYear();

        //计算每个订单的销售额并按日期分类
        for (Orders orders : ordersList) {
            //单笔订单金额
            BigDecimal orderAmount = orders.getHanfuQty().multiply(orders.getPrice());
            //订单日期
            LocalDate orderLocalDate = DateUtil.dateToLocalDate(orders.getCreateTime());

            // 获取订单的周数和年份
            int orderWeek = orderLocalDate.get(weekFields.weekOfYear());
            int orderYear = orderLocalDate.getYear();


            // 计算日销售额
            if (orderLocalDate.isEqual(currentLocalDate)) {
                dailySales = dailySales.add(orderAmount);
            }

            // 计算周销售额（同一年且同一周）
            if (orderYear == currentYear && orderWeek == currentWeek) {
                weeklySales = weeklySales.add(orderAmount);
            }

            // 计算月销售额
            if (orderLocalDate.getMonthValue() == currentLocalDate.getMonthValue() && orderLocalDate.getYear() == currentYear) {
                monthlySales = monthlySales.add(orderAmount);
            }
        }

        // TODO: 将计算结果封装到ReportInfoVO对象中
        ReportInfoVO reportInfoVO = new ReportInfoVO();
        reportInfoVO.setDailySales(dailySales);
        reportInfoVO.setWeeklySales(weeklySales);
        reportInfoVO.setMonthlySales(monthlySales);

        // TODO: 返回ReportInfoVO对象

        return ResultUtil.getResultData(reportInfoVO);
    }

    @Override
    public ResultData<ReportByHanfuTypeInfoVO> selectSalesInfoByHanfuType(QueryReportDTO queryReportDTO) {
        //查询符合条件的订单列表
        Example ordersExample = new Example(Orders.class);
        ordersExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("state",1)
                .andBetween("createTime",queryReportDTO.getStartTime(),queryReportDTO.getEndTime());
        List<Orders> ordersList = ordersMapper.selectByExample(ordersExample);

        ReportByHanfuTypeInfoVO reportByHanfuTypeInfoVO = new ReportByHanfuTypeInfoVO();
        //如果订单列表为空，返回空数据
        if(CollectionUtils.isEmpty(ordersList)){
            return ResultUtil.getResultData(reportByHanfuTypeInfoVO);
        }

        //存储不同汉服类型的销售数据
        Map<Long, BigDecimal> salesByTypesMap = new HashMap<>();

        //遍历订单列表，获取每个订单中的销售额
        for (Orders orders : ordersList) {
            //获取订单的汉服主键id
            Long hid = orders.getHid();
            //获取订单的汉服数量
            BigDecimal hanfuQty = orders.getHanfuQty();
            //获取订单的汉服价格
            BigDecimal price = orders.getPrice();

            //单笔订单金额 单价 * 数量
            BigDecimal salesAmount = hanfuQty.multiply(price);

            //根据汉服主键id获取汉服类型
            Hanfu hanfu = hanfuMapper.selectByPrimaryKey(hid);
            //获取汉服类型
            String hanfuTypeName = hanfu.getHanfuType();

            //根据汉服类型获取htid
            Example hanfuTypeExample = new Example(HanfuType.class);
            hanfuTypeExample.createCriteria().andEqualTo("isdelete",0)
                    .andEqualTo("hanfuType",hanfuTypeName);
            List<HanfuType> hanfuTypeList = hanfuTypeMapper.selectByExample(hanfuTypeExample);
            if(CollectionUtils.isEmpty(hanfuTypeList)){
                continue;
            }
            HanfuType hanfuType = hanfuTypeList.get(0);
            Long htid = hanfuType.getHtid();

            //根据htid获取销售数据
            salesByTypesMap.merge(htid, salesAmount, BigDecimal::add);
        }

        //将结果返回VO 对象中
        reportByHanfuTypeInfoVO.setSalesByTypesMap(salesByTypesMap);

        return ResultUtil.getResultData(reportByHanfuTypeInfoVO);
    }
}
