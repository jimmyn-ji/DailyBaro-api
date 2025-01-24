package com.project.hanfu.config;

/**
 * http状态消息
 *
 * @author: ShanZhu
 * @date: 2024-01-24
 */
public class HttpMsg {
    public static String ERROR_INPUT = "表单信息不正确";
    public static String ERROR_VERIFY = "用户名或密码错误";
    public static String ADD_USER_OK = "新建用户成功";
    public static String ADD_USER_FAILED = "新建用户失败，该账号已存在";
    public static String UPDATE_USER_OK = "修改用户信息成功";
    public static String UPDATE_USER_FAILED = "修改用户信息失败";
    public static String DELETE_USER_OK = "删除用户信息成功";
    public static String DELETE_USER_FAILED = "删除用户信息失败";
    
    public static String DELETE_TYPE_OK = "删除类型成功";
    public static String DELETE_TYPE_FAILED = "有商品绑定此类型，删除类型失败";
    public static String UPDATE_TYPE_OK = "修改类型成功";
    public static String UPDATE_TYPE_FAILED = "有商品绑定此类型，修改类型失败";
    public static String ADD_TYPE_OK = "新建类型成功";
    public static String ADD_TYPE_FAILED = "新建类型失败，该类型名称已存在";

    public static String DELETE_FLOWER_OK = "删除类型成功";
    public static String DELETE_FLOWER_FAILED = "有商品绑定此类型，删除类型失败";
    public static String UPDATE_FLOWER_OK = "修改类型成功";
    public static String UPDATE_FLOWER_FAILED = "有商品绑定此类型，修改类型失败";
    public static String ADD_FLOWER_OK = "新建类型成功";
    public static String ADD_FLOWER_FAILED = "新建类型失败，该类型名称已存在";

    public static String NO_TYPE_NOW = "当前没有花朵种类，请先创建！";

    public static String UPDATE_PIC_OK = "更新图片成功";
    public static String UPDATE_PIC_FAILED = "更新图片失败";

    public static String INVALID_PARAM = "参数不合法";

    public static String INVALID_USER = "登录失效，请重新登录";

    public static String ADD_CART_OK = "加入购物车成功";
    public static String ADD_CART_FAILED = "加入购物车失败";

    public static String BUY_OK = "下单成功";

    public static String UPDATE_ORDER_OK = "订单发货状态更新成功";
    public static String ERROR_FILE_TYPE = "错误的文件类型";

    public static String GOODS_UP_OK = "商品上架成功";
    public static String GOODS_DOWN_OK = "商品下架成功";
}
