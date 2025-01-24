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

    @Select("select * from species where species_name like concat('%',#{searchKey},'%');")
    List<Species> find(String searchKey);

    @Select("select * from species;")
    List<Species> findAll();

    @Update("update species set species_name = #{species_name} where id = #{id};")
    int update(Species species);

    @Delete("delete from species where id = #{id};")
    int delete(int id);

    @Insert("insert into species(species_name) values(#{species_name});")
    int add(Species species);

}




