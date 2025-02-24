package com.project.transactions.controller;

import com.project.transactions.model.dto.UidDTO;
import com.project.transactions.model.dto.UpdatePwdDTO;
import com.project.transactions.model.dto.UpdateUserInfoDTO;
import com.project.transactions.model.vo.UserInfoVO;
import com.project.transactions.result.ResultData;
import com.project.transactions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;



@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    //查询用户信息
    @RequestMapping("/selectUserInfo")
    public ResultData<UserInfoVO> selectUserInfo(@RequestBody UidDTO uidDTO){
        return userService.selectUserInfo(uidDTO);
    }

    //查询所有人信息
    @RequestMapping("/selectAllUserInfo")
    public ResultData<List<UserInfoVO>> selectAllUserInfo(){
        return userService.selectAllUserInfo();
    }

    //修改用户信息
    @RequestMapping("/updateUserInfo")
    public ResultData<UserInfoVO> updateUserInfo(@RequestBody UpdateUserInfoDTO updateUserInfoDTO){
        return userService.updateUserInfo(updateUserInfoDTO);
    }

    //修改用户状态
    @RequestMapping("/updateStatus")
    public ResultData<UserInfoVO> updateStatus(@RequestBody UpdateUserInfoDTO updateUserInfoDTO) {
        return userService.updateStatus(updateUserInfoDTO);
    }

    //修改密码
    @RequestMapping("/updatePwd")
    public ResultData<UserInfoVO> updatePwd(@RequestBody UpdatePwdDTO updatePwdDTO){
        return userService.updatePwd(updatePwdDTO);
    }

}

