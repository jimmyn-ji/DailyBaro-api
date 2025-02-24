package com.project.transactions.service;

import java.util.List;
import com.project.transactions.model.dto.UidDTO;
import com.project.transactions.model.dto.UpdateUserInfoDTO;
import com.project.transactions.model.vo.UserInfoVO;
import com.project.transactions.result.ResultData;
import com.project.transactions.model.dto.UpdatePwdDTO;

public interface UserService {
    // 查询用户信息
    ResultData<UserInfoVO> selectUserInfo(UidDTO uidDTO);
    // 查询所有人信息
    ResultData<List<UserInfoVO>> selectAllUserInfo();
    // 修改用户信息
    ResultData<UserInfoVO> updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO);
    // 修改用户状态
    ResultData<UserInfoVO> updateStatus(UpdateUserInfoDTO updateUserInfoDTO);
    // 修改密码
    ResultData<UserInfoVO> updatePwd(UpdatePwdDTO updatePwdDTO);
}
