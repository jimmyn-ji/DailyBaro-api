package com.project.mapper;

import com.project.model.NursingProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NursingProductMapper {
    // 获取所有护理商品
    List<NursingProduct> getAllProducts();

    // 根据ID查询护理商品
    NursingProduct getProductById(Integer productId);

    // 新增护理商品
    int insertProduct(NursingProduct product);

    // 更新护理商品
    int updateProduct(NursingProduct product);

    // 删除护理商品
    int deleteProduct(Integer productId);

    // 根据名称查询护理商品
    NursingProduct getProductByName(String name);
}