package com.project.transactions.result;


import java.util.List;

import com.project.transactions.menu.StatusCode;

public class ResultUtil {

    /**
     * 获取查询结果，带 数据和总条数
     * @param t
     * @param totalItems
     * @return
     * @param <T>
     */
    public static <T> ResultQuery<T> getResultQuery(List<T> t, Integer totalItems) {
        ResultQuery<T> res = new ResultQuery<T>();
        // 设置总条数
        res.setTotalItems(totalItems);
        // 设置数据
        res.setData(t);
        // 调用成功.
        res.setSuccess(true);
        // 设置 标识码
        res.setCode(StatusCode.SUCCESS);
        // 设置消息
        res.setMessage("操作成功！");
        return res;
    }


    /**
     * 获取查询结果，带数据
     * @param t
     * @return
     * @param <T>
     */
    public static <T> ResultData<T> getResultData(T t) {
        // 封装数据
        ResultData<T> res = new ResultData<T>();
        // 设置数据
        res.setData(t);
        // 调用成功
        res.setSuccess(true);
        // 设置 标识码
        res.setCode(StatusCode.SUCCESS);
        // 设置消息
        res.setMessage("操作成功！");
        return res;
    }
}
