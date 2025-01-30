package com.project.hanfu.service.impl;

import com.project.hanfu.exception.CustomException;
import com.project.hanfu.mapper.UserMapper;
import com.project.hanfu.model.User;
import com.project.hanfu.model.dto.UserPwDTO;
import com.project.hanfu.model.vo.UserInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.LoginService;
import com.project.hanfu.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     * @param userPwDTO
     * @return
     */
    @Override
    public ResultData<UserInfoVO> pwLogin(UserPwDTO userPwDTO) {
        //验证表单信息
        if(StringUtils.isEmpty(userPwDTO.getAccount())|| StringUtils.isEmpty(userPwDTO.getPassword())|| StringUtils.isEmpty(userPwDTO.getRole())){
            throw new CustomException("请核对信息");
        }

        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("account",userPwDTO.getAccount())
                .andEqualTo("password",userPwDTO.getPassword())
                .andEqualTo("role",userPwDTO.getRole());
        List<User> users = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(users)){
            throw new CustomException("用户名或密码错误");
        }

        UserInfoVO userInfoVO =new UserInfoVO();
        User user = users.get(0);
        userInfoVO.setAccount(user.getAccount());
        userInfoVO.setPassword(user.getPassword());
        userInfoVO.setRole(user.getRole());
        userInfoVO.setPhoneNo(user.getPhoneNo());
        userInfoVO.setName(user.getName());
        userInfoVO.setAddress(user.getAddress());
        return ResultUtil.getResultData(userInfoVO);
    }
}
