package com.project.transactions.service.impl;

import com.project.transactions.exception.CustomException;
import com.project.transactions.mapper.UserMapper;
import com.project.transactions.model.User;
import com.project.transactions.model.dto.UidDTO;
import com.project.transactions.model.dto.UpdatePwdDTO;
import com.project.transactions.model.dto.UpdateUserInfoDTO;
import com.project.transactions.model.vo.UserInfoVO;
import com.project.transactions.result.ResultData;
import com.project.transactions.result.ResultUtil;
import com.project.transactions.service.UserService;
import com.project.transactions.util.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private SnowFlake snowFlake;

    // 查询用户信息
    @Override
    public ResultData<UserInfoVO> selectUserInfo(UidDTO uidDTO) {
        //获取用户id
        Long uid = uidDTO.getUid();

        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            throw new CustomException("用户不存在");
        }

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        return ResultUtil.getResultData(userInfoVO);
    }

    // 查询所有人信息
    @Override
    public ResultData<List<UserInfoVO>> selectAllUserInfo() {
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        //获取用户id
        //获取所有用户信息
        List<User> userList = userMapper.selectAll();
        if (userList == null) {
            return ResultUtil.getResultData(userInfoVOList);
        }

        for (User user : userList) {
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtils.copyProperties(user, userInfoVO);
            userInfoVOList.add(userInfoVO);
        }
        //移除管理员账户
        userInfoVOList.removeIf(userInfoVO -> userInfoVO.getUid() == 1);
        return ResultUtil.getResultData(userInfoVOList);
    }

    // 修改用户信息
    @Override
    public ResultData<UserInfoVO> updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO) {
        //获取用户id
        Long uid = updateUserInfoDTO.getUid();
        //获取用户信息
        String phone = updateUserInfoDTO.getPhone();
        String email = updateUserInfoDTO.getEmail();

        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            throw new CustomException("用户不存在");
        }

        BeanUtils.copyProperties(updateUserInfoDTO, user);
        user.setUid(uid);
        userMapper.updateByPrimaryKeySelective(user);

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        return ResultUtil.getResultData(userInfoVO);
    }

    // 修改用户状态
    @Override
    public ResultData<UserInfoVO> updateStatus(UpdateUserInfoDTO updateUserInfoDTO) {
        //获取用户id
        Long uid = updateUserInfoDTO.getUid();
        //获取用户信息
        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            throw new CustomException("用户不存在");
        }
        //修改用户状态
        user.setStatus(updateUserInfoDTO.getStatus());
        userMapper.updateByPrimaryKeySelective(user);
        //返回用户信息
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        //返回结果
        return ResultUtil.getResultData(userInfoVO);
    }

    // 修改密码
    @Override
    public ResultData<UserInfoVO> updatePwd(UpdatePwdDTO updatePwdDTO) {
        //获取用户id
        Long uid = updatePwdDTO.getUid();
        //获取旧密码
        String oldPassword = updatePwdDTO.getOldPassword();
        //获取新密码
        String newPassword = updatePwdDTO.getNewPassword();
        //获取用户信息
        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            throw new CustomException("用户不存在");
        }
        //判断旧密码是否正确
        if (!user.getPassword().equals(oldPassword)) {
            throw new CustomException("旧密码错误");
        }
        //修改密码
        user.setPassword(newPassword);
        userMapper.updateByPrimaryKeySelective(user);
        //返回用户信息
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        //返回结果
        return ResultUtil.getResultData(userInfoVO);
    }

}
