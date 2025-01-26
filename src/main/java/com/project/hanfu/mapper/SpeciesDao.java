package com.project.hanfu.mapper;

import com.project.hanfu.model.Species;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeciesDao {


    @Select("select * from species;")
    List<Species> findAll();

    @Update("update species set species_name = #{species_name} where id = #{id};")
    int update(Species species);


}




