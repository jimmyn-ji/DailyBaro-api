package com.project.mapper;

import com.project.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectAllUsers();

    User getMyInfo(Long uid);

    // 保留原有的方法
    User selectByAccount(String account);

    // 根据微信openid查询用户
    @Select("SELECT * FROM user WHERE wx_openid = #{openid} AND isdelete = 0")
    User selectByWxOpenid(String openid);

    // 插入用户(需包含wx_openid字段)
    @Insert("INSERT INTO user(account, password, wx_openid, phone, status, isdelete) " +
            "VALUES(#{account}, #{password}, #{wxOpenid}, #{phone}, #{status}, #{isdelete})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    int insertUser(User user);

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 受影响的行数
     */
    int updateUserInfo(User user);

    /**
     * 修改用户密码
     * @param uid 用户 ID
     * @param newPassword 新密码
     * @return 受影响的行数
     */
    int updateUserPassword(Long uid, String newPassword);
}