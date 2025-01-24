package com.project.hanfu.service;

import com.project.hanfu.model.dto.UserPwDTO;
import com.project.hanfu.model.vo.UserInfoVO;
import com.project.hanfu.result.ResultData;

public interface LoginService {

    /**
     * 登录接口
     * @param userPwDTO
     * @return
     */
    ResultData<UserInfoVO> pwLogin(UserPwDTO userPwDTO);
}
