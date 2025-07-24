package com.project.controller;

import com.github.pagehelper.PageInfo;
import com.project.model.User;
import com.project.model.dto.UpdatePwdDTO;
import com.project.model.dto.UpdateUserInfoDTO;
import com.project.service.UserService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询用户列表
     * @param pageNum 页码，默认为1
     * @param pageSize 每页大小，默认为10
     * @return 分页用户列表
     */
    @GetMapping("/getUsersByPage")
    public Result<PageInfo<User>> getUsersByPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<User> pageInfo = userService.getUsersByPage(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @GetMapping("/getMyInfo/{uid}")
    public Result<User> getMyInfo(@PathVariable Long uid) {
        User user = userService.getMyInfo(uid);
        return Result.success(user);
    }

    /**
     * 修改密码
     * @return 操作结果
     */
    @RequestMapping("/changePassword/{uid}")
    public Result<User> changePassword(@RequestBody UpdatePwdDTO updatePwdDTO) {
        userService.changePassword(updatePwdDTO);
        return Result.success();
    }

    /**
     * 修改个人信息（PUT 方式，兼容前端）
     * @param dto 用户信息
     * @return 操作结果
     */
    @PutMapping("/updateUserInfo")
    public Result<String> updateUserInfo(@RequestBody UpdateUserInfoDTO dto) {
        userService.updateUserInfo(dto);
        return Result.success("个人信息修改成功");
    }

    /**
     * 兼容前端 /user/selectUserInfo POST 请求
     */
    @PostMapping("/selectUserInfo")
    public Result<User> selectUserInfo(@RequestBody Map<String, Object> params) {
        User user = null;
        if (params.containsKey("uid")) {
            Long uid = Long.valueOf(params.get("uid").toString());
            user = userService.getMyInfo(uid);
        } else if (params.containsKey("account")) {
            user = userService.getByAccount(params.get("account").toString());
        }
        if (user == null) {
            return Result.fail("用户不存在");
        }
        return Result.success(user);
    }

    /**
     * 账号删除（允许未登录操作）
     * @param uid 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{uid}")
    public Result<Void> deleteUser(@PathVariable Long uid) {
        userService.deleteUser(uid);
        return Result.success();
    }

    /**
     * 注销账号（需密码校验）
     */
    @PostMapping("/deleteWithPwd")
    public Result<Void> deleteUserWithPwd(@RequestParam Long uid, @RequestParam String password) {
        boolean ok = userService.deleteUserWithPwd(uid, password);
        if (ok) {
            return Result.success();
        } else {
            return Result.fail("密码错误，注销失败");
        }
    }
}