package com.project.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.mapper.UserMapper;
import com.project.model.User;
import com.project.model.dto.UpdatePwdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询用户
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    public PageInfo<User> getUsersByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectAllUsers();
        return new PageInfo<>(users);
    }

    public User getMyInfo(Long uid) {
        return userMapper.getMyInfo(uid);
    }

    /**
     * 修改密码
     */
    public void changePassword(UpdatePwdDTO updatePwdDTO) {
        User user = userMapper.getMyInfo(updatePwdDTO.getUid());
        if (user != null && user.getPassword().equals(updatePwdDTO.getOldPassword())) {
            userMapper.updateUserPassword(updatePwdDTO.getUid(), updatePwdDTO.getNewPassword());
        } else {
            throw new RuntimeException("旧密码错误");
        }
    }

    /**
     * 修改个人信息
     * @param user 用户信息
     */
    public void updateUserInfo(User user) {
        userMapper.updateUserInfo(user);
    }
}