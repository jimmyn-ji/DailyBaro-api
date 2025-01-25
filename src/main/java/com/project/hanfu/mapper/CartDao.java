package com.project.hanfu.mapper;

import com.project.hanfu.model.Cart;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao {

    @Select("select * from carts where flower like concat('%',#{searchKey},'%')  and account = #{account};")
    List<Cart> find(@Param("searchKey") String searchKey, @Param("account") String account);

    @Select("select * from carts;")
    List<Cart> findAll();


    @Select("select * from carts where uid = #{uid};")
    List<Cart> queryByUid(int uid);

    @Update("update carts set name = #{name},password = #{password},phone = #{phone},address = #{address} where id = #{id};")
    int update(Cart cart);


}




