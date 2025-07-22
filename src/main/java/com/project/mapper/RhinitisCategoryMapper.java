package com.project.mapper;

import com.project.model.RhinitisType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RhinitisCategoryMapper {
    // 获取所有类别
    List<RhinitisType> getAllCategories();

    // 根据ID查询类别
    RhinitisType getCategoryById(Long categoryId);

    // 新增类别
    int insertCategory(RhinitisType category);

    // 更新类别
    int updateCategory(RhinitisType category);

    // 删除类别
    int deleteCategory(Long categoryId);

    // 根据名称查询类别
    RhinitisType getCategoryByName(String name);
}