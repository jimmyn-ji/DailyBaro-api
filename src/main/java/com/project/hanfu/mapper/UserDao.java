package com.project.hanfu.mapper;

import com.project.hanfu.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Select("select * from users where (account like concat('%',#{searchKey},'%')  or name like concat('%',#{searchKey},'%')) and role = 'user';")
    List<User> find(String searchKey);

    @Select("select * from users where id = #{id};")
    User queryById(Integer id);

    @Select("select * from users;")
    List<User> findAll();

    @Select("select id from users where account = #{account} and role = 'user';")
    Integer queryIdByAccount(String account);

    @Delete("delete from users where id = #{id};")
    int delete(int id);

    @Insert("insert into users(account,name,password,phone,address,role) " +
            "values(#{account},#{name},#{password},#{phone},#{address},'user');")
    int add(User user);

}




