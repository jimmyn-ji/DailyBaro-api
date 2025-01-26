package com.project.hanfu.controller;

import com.project.hanfu.model.dto.UserPwDTO;
import com.project.hanfu.model.vo.UserInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录 控制层
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录接口
     * @param userPwDTO
     * @return
     */
    @RequestMapping("/doLogin")
    public ResultData<UserInfoVO> pwLogin(@RequestBody UserPwDTO userPwDTO){
        return loginService.pwLogin(userPwDTO);
    }
}
