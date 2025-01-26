package com.project.hanfu.mapper;

import com.project.hanfu.model.Flower;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowersDao {

    @Update("update flowers set name = #{name}, species_name = #{species_name}, price = #{price}, detail = #{detail} where id = #{id};")
    Integer update(Flower flower);



    @Insert("insert into flowers(name,species_name,price,detail,state) values(#{name},#{species_name},${price},#{detail},1);")
    Integer add(Flower flower);

}




