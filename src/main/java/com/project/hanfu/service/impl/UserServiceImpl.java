package com.project.hanfu.service.impl;

import com.project.hanfu.exception.CustomException;
import com.project.hanfu.mapper.UserDao;
import com.project.hanfu.mapper.UserMapper;
import com.project.hanfu.model.User;
import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.InsertUserDTO;
import com.project.hanfu.model.dto.UpdateUserInfoDTO;
import com.project.hanfu.model.vo.UserInfoVO;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.result.ResultUtil;
import com.project.hanfu.service.UserService;
import com.project.hanfu.util.CollectionUtils;
import com.project.hanfu.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userdao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SnowFlake snowFlake;

    /**
     * 根据用户账号查询用户信息
     * @param accountDTO
     * @return
     */
    @Override
    public ResultData<UserInfoVO> queryInfoByAccount(AccountDTO accountDTO) {
        String account = accountDTO.getAccount();
        if(StringUtil.isEmpty(account)){
            throw new CustomException("请重新登录");
        }

        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete")
                .andEqualTo("account",account);
//                .andEqualTo("role","user");
        List<User> users = userMapper.selectByExample(userExample);

        UserInfoVO userInfoVO = new UserInfoVO();
        if(CollectionUtils.isNotEmpty(users)){
            users.forEach(user -> {
                userInfoVO.setAccount(user.getAccount());
                userInfoVO.setName(user.getName());
                userInfoVO.setPhoneNo(user.getPhoneNo());
                userInfoVO.setAddress(user.getAddress());
                userInfoVO.setPassword(user.getPassword());
            });
        }
        return ResultUtil.getResultData(userInfoVO);
    }

    /**
     * 根据用户id更新用户信息
     * @param updateUserInfoDTO
     * @return
     */
    @Override
    @Transactional
    public ResultData<UserInfoVO> updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO) {
        //获取用户id
        Long uid = updateUserInfoDTO.getUid();
        String account = updateUserInfoDTO.getAccount();
        String password = updateUserInfoDTO.getPassword();
        String name = updateUserInfoDTO.getName();
        String phoneNo = updateUserInfoDTO.getPhoneNo();
        String address = updateUserInfoDTO.getAddress();

        //查询用户信息
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("account",account);
        List<User> users = userMapper.selectByExample(userExample);

        User user = users.get(0);
        //更新用户信息
        user.setPassword(password);
        user.setAccount(account);
        user.setName(name);
        user.setPhoneNo(phoneNo);
        user.setAddress(address);
        userMapper.updateByPrimaryKeySelective(user);

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setAccount(user.getAccount());
        userInfoVO.setName(user.getName());
        userInfoVO.setPhoneNo(user.getPhoneNo());
        userInfoVO.setAddress(user.getAddress());
        userInfoVO.setPassword(user.getPassword());
        userInfoVO.setRole(user.getRole());

        return ResultUtil.getResultData(userInfoVO);
    }


    /**
     * 用户注册接口
     * @param insertUserDTO
     * @return
     */
    @Override
    public ResultData<UserInfoVO> register(InsertUserDTO insertUserDTO) {
        String account = insertUserDTO.getAccount();
        String password = insertUserDTO.getPassword();
        String name = insertUserDTO.getName();
        String phone = insertUserDTO.getPhoneNo();
        String address = insertUserDTO.getAddress();

        //判断用户账号是否已存在
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("isdelete",0)
                .andEqualTo("account",account);
        List<User> users = userMapper.selectByExample(userExample);
        //若存在
        if(CollectionUtils.isNotEmpty(users)){
            throw new CustomException("用户账号已存在");
        }

        //不存在，插入用户信息
        User insertUser = new User();
        insertUser.setUid(snowFlake.nextId());
        insertUser.setAccount(account);
        insertUser.setPassword(password);
        insertUser.setName(name);
        insertUser.setPhoneNo(phone);
        insertUser.setAddress(address);
        insertUser.setRole("user");
        userMapper.insertSelective(insertUser);

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setAccount(insertUser.getAccount());
        userInfoVO.setName(insertUser.getName());
        userInfoVO.setPhoneNo(insertUser.getPhoneNo());
        userInfoVO.setAddress(insertUser.getAddress());
        userInfoVO.setPassword(insertUser.getPassword());
        userInfoVO.setRole(insertUser.getRole());

        return ResultUtil.getResultData(userInfoVO);
    }


    @Override
    public int delete(int uid) {
        return userdao.delete(uid);
    }



    @Override
    public List<User> find(String searchKey) {
        return userdao.find(searchKey);
    }

}
