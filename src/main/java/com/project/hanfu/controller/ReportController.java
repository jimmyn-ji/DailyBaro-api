package com.project.hanfu.controller;

import com.project.hanfu.model.dto.QueryReportDTO;
import com.project.hanfu.model.vo.ReportByHanfuTypeInfoVO;
import com.project.hanfu.model.vo.ReportInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计报表模块
 */
@RestController
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping("/selectSalesInfo")
    ResultData<ReportInfoVO> selectSalesInfo(@RequestBody QueryReportDTO queryReportDTO){
        return reportService.selectSalesInfo(queryReportDTO);
    }

    @RequestMapping("/selectSalesInfoByHanfuType")
    ResultData<ReportByHanfuTypeInfoVO> selectSalesInfoByHanfuType(@RequestBody QueryReportDTO queryReportDTO){
        return reportService.selectSalesInfoByHanfuType(queryReportDTO);
    }
}
