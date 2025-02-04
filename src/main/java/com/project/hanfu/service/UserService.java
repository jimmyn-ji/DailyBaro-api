package com.project.hanfu.service;

import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.InsertUserDTO;
import com.project.hanfu.model.dto.QueryUserDTO;
import com.project.hanfu.model.dto.UpdateUserInfoDTO;
import com.project.hanfu.model.vo.UserInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultQuery;
/**
 * 用户 服务层
 */
public interface UserService {

    /**
     * 根据用户账号查询用户信息
     * @param accountDTO
     * @return
     */
    ResultData<UserInfoVO> queryInfoByAccount(AccountDTO accountDTO);

    /**
     * 更新用户信息
     * @param updateUserInfoDTO
     * @return
     */
    ResultData<UserInfoVO> updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO);

    /**
     * 注册用户信息
     * @param insertUserDTO
     * @return
     */
    ResultData<UserInfoVO> register(InsertUserDTO insertUserDTO);

    /**
     * 管理员查询客户信息
     * @param queryUserDTO
     * @return
     */
    ResultQuery<UserInfoVO> queryCustomerInfo(QueryUserDTO queryUserDTO);

}
