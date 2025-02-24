package com.project.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.transactions.model.dto.UserPwDTO;
import com.project.transactions.model.dto.UserRegisterDTO;
import com.project.transactions.model.vo.UserInfoVO;
import com.project.transactions.result.ResultData;
import com.project.transactions.service.LoginService;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/doLogin")
    public ResultData<UserInfoVO> doLogin(@RequestBody UserPwDTO userPwDTO) {
        return loginService.doLogin(userPwDTO);
    }

    @RequestMapping("/doRegister")
    public ResultData<UserInfoVO> doRegister(@RequestBody UserRegisterDTO userRegisterDTO) {
        return loginService.doRegister(userRegisterDTO);
    }
    
}
