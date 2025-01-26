package com.project.hanfu.mapper;

import com.project.hanfu.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Select("select * from user where (account like concat('%',#{searchKey},'%')  or name like concat('%',#{searchKey},'%')) and role = 'user';")
    List<User> find(String searchKey);

    @Select("select * from user where id = #{id};")
    User queryById(Integer id);

    @Select("select * from user;")
    List<User> findAll();

    @Select("select id from user where account = #{account} and role = 'user';")
    Integer queryIdByAccount(String account);

    @Delete("delete from user where id = #{id};")
    int delete(int id);

    @Insert("insert into user(account,name,password,phone,address,role) " +
            "values(#{account},#{name},#{password},#{phone},#{address},'user');")
    int add(User user);

}




