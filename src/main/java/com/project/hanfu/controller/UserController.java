package com.project.hanfu.controller;

import com.project.hanfu.model.dto.*;
import com.project.hanfu.model.vo.UserInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
import com.project.hanfu.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用户控制层
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/queryInfoByAccount")
    ResultData<UserInfoVO> queryInfoByAccount(@RequestParam("account") String account) {
//        从 URL 查询字符串中接收数据并转化为 JSON
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount(account);
        return userService.queryInfoByAccount(accountDTO);
    }

    /**
     * 管理员查询客户信息接口
     * @param page,searchKey
     * @return
     */
    @RequestMapping("/find")
    ResultQuery<UserInfoVO> queryCustomerInfo(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey){
        QueryUserDTO queryUserDTO = new QueryUserDTO();
        queryUserDTO.setPage(page);
        queryUserDTO.setSearchKey(searchKey);
        return userService.queryCustomerInfo(queryUserDTO);
    }

    /**
     * 用户注册接口
     * @param insertUserDTO
     * @return
     */
    @RequestMapping("/create")
    ResultData<UserInfoVO> register(@RequestBody InsertUserDTO insertUserDTO) {
        return userService.register(insertUserDTO);
    }

    /**
     * 更新用户信息
     * @param updateUserInfoDTO
     * @return
     */
    @RequestMapping("/update")
    public ResultData<UserInfoVO> updateUserInfo(@RequestBody UpdateUserInfoDTO updateUserInfoDTO){
        return userService.updateUserInfo(updateUserInfoDTO);
    }

}

