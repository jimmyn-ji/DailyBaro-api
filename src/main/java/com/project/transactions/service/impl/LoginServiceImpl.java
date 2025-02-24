package com.project.transactions.service.impl;

import com.project.transactions.exception.CustomException;
import com.project.transactions.mapper.UserMapper;
import com.project.transactions.model.User;
import com.project.transactions.model.dto.UserPwDTO;
import com.project.transactions.model.dto.UserRegisterDTO;
import com.project.transactions.model.vo.UserInfoVO;
import com.project.transactions.result.ResultData;
import com.project.transactions.result.ResultUtil;
import com.project.transactions.service.LoginService;
import com.project.transactions.util.CollectionUtils;
import com.project.transactions.util.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SnowFlake snowflake;


    /**
     * 密码登录
     * @param userPwDTO
     * @return
     */
    @Override
    public ResultData<UserInfoVO> doLogin(UserPwDTO userPwDTO) {
        // 获取账号
        String account = userPwDTO.getAccount();
        // 获取密码
        String password = userPwDTO.getPassword();

        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("status", 0)
                .andEqualTo("account", account)
                .andEqualTo("password", password);
        // 查询用户信息
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            throw new CustomException("请检查账号密码");
        }

        // 获取用户信息
        User user = userList.get(0);

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);

        
        return ResultUtil.getResultData(userInfoVO);
    }

    /**
     * 注册
     * @param userRegisterDTO
     * @return
     */
    @Override
    public ResultData<UserInfoVO> doRegister(UserRegisterDTO userRegisterDTO) {

        // 获取账号
        String account = userRegisterDTO.getAccount();
        
        // 检查账号是否已存在
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete", 0)
        .andEqualTo("account", account);
        List<User> userList = userMapper.selectByExample(userExample);
        if (!userList.isEmpty()) {
            throw new CustomException("账号已存在");
        }
        
        //创建用户
        User insertUser = new User();
        BeanUtils.copyProperties(userRegisterDTO, insertUser);
        insertUser.setUid(snowflake.nextId());
        insertUser.setRole(0);
        insertUser.setStatus(0);
        insertUser.setIsdelete(0);
        userMapper.insertSelective(insertUser);

        // 返回用户信息
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(insertUser, userInfoVO);

        return ResultUtil.getResultData(userInfoVO);
    }
}