package com.project.hanfu.result;

import com.project.hanfu.menu.StatusCode;

import java.util.List;

public class ResultUtil {

    /**
     * 获取基本结果，不需要 数据。
     * @param success
     * @param code
     * @param message
     * @return
     */
    public static ResultBase getResultBase(boolean success, StatusCode code, String message) {
        ResultBase res = new ResultBase();
        res.setCode(code);
        res.setMessage(message);
        res.setSuccess(success);
        return res;
    }


    /**
     * 获取基本结果，不需要 数据。
     * @param message
     * @return
     */
    public static ResultBase getResultBase(String message) {
        ResultBase res = new ResultBase();
        res.setCode(StatusCode.SUCCESS);
        res.setMessage(message);
        res.setSuccess(true);
        return res;
    }


    public static ResultBase getResultBase(boolean success) {
        ResultBase res = new ResultBase();
        res.setSuccess(success);
        if (success) {
            res.setCode(StatusCode.SUCCESS);
            res.setMessage("操作成功！");
        } else {
            res.setCode(StatusCode.ERROR);
            res.setMessage("操作失败！");
        }
        return res;
    }


    /**
     * 获取基本结果，不需要 数据。
     * @return
     */
    public static ResultBase getResultBase() {
        ResultBase res = new ResultBase();
        res.setCode(StatusCode.SUCCESS);
        res.setMessage("操作成功！");
        res.setSuccess(true);
        return res;
    }


    /**
     * 获取查询结果，带 数据和总条数
     * @param success
     * @param code
     * @param message
     * @param t
     * @param totalItems
     * @return
     * @param <T>
     */
    public static <T> ResultQuery<T> getResultQuery(boolean success, StatusCode code, String message, List<T> t,
                                                    Integer totalItems) {
        ResultQuery<T> res = new ResultQuery<T>();
        // 设置总条数
        res.setTotalItems(totalItems);
        // 设置数据
        res.setData(t);
        // 调用成功.
        res.setSuccess(success);
        // 设置 标识码
        res.setCode(code);
        // 设置消息
        res.setMessage(message);
        return res;
    }


    /**
     * 获取查询结果，带 数据和总条数
     * @param message
     * @param t
     * @param totalItems
     * @return
     * @param <T>
     */
    public static <T> ResultQuery<T> getResultQuery(String message, List<T> t, Integer totalItems) {
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
        res.setMessage(message);
        return res;
    }

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
     * @param success
     * @param code
     * @param message
     * @param t
     * @return
     * @param <T>
     */
    public static <T> ResultData<T> getResultData(boolean success, StatusCode code, String message, T t) {
        // 封装数据
        ResultData<T> res = new ResultData<T>();
        // 设置数据
        res.setData(t);
        // 调用成功.
        res.setSuccess(success);
        // 设置 标识码
        res.setCode(code);
        // 设置消息
        res.setMessage(message);
        return res;
    }


    /**
     * 获取查询结果，带数据
     * @param message
     * @param t
     * @return
     * @param <T>
     */
    public static <T> ResultData<T> getResultData(String message, T t) {
        // 封装数据
        ResultData<T> res = new ResultData<T>();
        // 设置数据
        res.setData(t);
        // 调用成功.
        res.setSuccess(true);
        // 设置 标识码
        res.setCode(StatusCode.SUCCESS);
        // 设置消息
        res.setMessage(message);
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
        // 调用成功.
        res.setSuccess(true);
        // 设置 标识码
        res.setCode(StatusCode.SUCCESS);
        // 设置消息
        res.setMessage("操作成功！");
        return res;
    }

}
