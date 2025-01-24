package com.project.hanfu.service;

import com.project.hanfu.model.User;
import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.InsertUserDTO;
import com.project.hanfu.model.dto.UpdateUserInfoDTO;
import com.project.hanfu.model.vo.UserInfoVO;
import com.project.hanfu.result.ResultData;

import java.util.List;

/**
 * 用户 服务层
 *
 * @author: ShanZhu
 * @date: 2024-01-24
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
    ResultData<UserInfoVO> update(UpdateUserInfoDTO updateUserInfoDTO);

    /**
     * 注册用户信息
     * @param insertUserDTO
     * @return
     */
    ResultData<UserInfoVO> register(InsertUserDTO insertUserDTO);

    int delete(int uid);
    List<User> find(String searchKey);
}
