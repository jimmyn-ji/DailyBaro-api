package com.project.hanfu.mapper;

import com.project.hanfu.model.Flower;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowersDao {

    @Select("select * from flowers where name like concat('%',#{searchKey},'%');")
    List<Flower> findAll(String searchKey);

//    @Select("select price from flowers where id = #{fid};")
//    Float queryPrice(int fid);

    @Update("update flowers set name = #{name}, species_name = #{species_name}, price = #{price}, detail = #{detail} where id = #{id};")
    Integer update(Flower flower);

    @Update("update flowers set img_guid = #{img_guid} where id = #{id};")
    Integer updateImg(@Param("img_guid") String img_guid,@Param("id") Integer id);

    @Update("update flowers set state = ${state} where id = #{id};")
    Integer changeState(Flower flower);

    @Delete("delete from flowers where id = #{id};")
    Integer delete(int id);

    @Insert("insert into flowers(name,species_name,price,detail,state) values(#{name},#{species_name},${price},#{detail},1);")
    Integer add(Flower flower);

}




