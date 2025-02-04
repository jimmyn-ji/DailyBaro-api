package com.project.hanfu.service;

import com.project.hanfu.model.dto.QueryReportDTO;
import com.project.hanfu.model.vo.ReportByHanfuTypeInfoVO;
import com.project.hanfu.model.vo.ReportInfoVO;
import com.project.hanfu.result.ResultData;

public interface ReportService {

    /**
     * 查询营业额信息接口
     * @param queryReportDTO
     * @return
     */
    ResultData<ReportInfoVO> selectSalesInfo(QueryReportDTO queryReportDTO);

    /**
     * 查询汉服类型销售信息接口
     * @param queryReportDTO
     * @return
     */
    ResultData<ReportByHanfuTypeInfoVO> selectSalesInfoByHanfuType(QueryReportDTO queryReportDTO);
}
