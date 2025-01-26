package com.project.hanfu.mapper;

import com.project.hanfu.model.Cart;
import com.project.hanfu.model.Orders;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {

    @Select("select * from orders where flower like concat('%',#{searchKey},'%')  and uid = #{uid};")
    List<Orders> find(@Param("searchKey") String searchKey, @Param("uid") int uid);

    @Select("select * from orders where flower like concat('%',#{searchKey},'%');")
    List<Orders> findAll(String searchKey);


    @Select("select * from orders where fid = #{fid} and uid = #{uid};")
    Orders checkIsAdded(Orders orders);



    @Select("select * from orders where uid = #{uid};")
    List<Orders> queryByUid(int uid);

    @Update("update orders set name = #{name},password = #{password},phone = #{phone},address = #{address} where id = #{id};")
    int update(Orders orders);

    @Delete("delete from orders where id = #{id};")
    int delete(int id);

    @Insert("insert into orders(flower,amount,price,state,uid) " +
            "values(#{flower},#{amount},#{price},0,#{uid});")
    int add(Cart cart);

}




