package com.project.transactions.service;

import com.project.transactions.model.dto.UserPwDTO;
import com.project.transactions.model.dto.UserRegisterDTO;
import com.project.transactions.model.vo.UserInfoVO;
import com.project.transactions.result.ResultData;

public interface LoginService {
    // 登录
    ResultData<UserInfoVO> doLogin(UserPwDTO userPwDTO);
    // 注册
    ResultData<UserInfoVO> doRegister(UserRegisterDTO userRegisterDTO);
}