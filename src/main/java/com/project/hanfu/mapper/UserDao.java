package com.project.hanfu.mapper;

import com.project.hanfu.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Select("select id from user where account = #{account} and role = 'user';")
    Integer queryIdByAccount(String account);



}




