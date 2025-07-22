package com.project.mapper;

import com.project.model.RhinitisType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RhinitisTypeMapper {

    // 查询所有鼻炎类型
    List<RhinitisType> selectAllRhinitisTypes();

    // 根据ID查询鼻炎类型
    RhinitisType getRhinitisTypeById(Long rid);

    // 新增鼻炎类型
    int insertRhinitisType(RhinitisType rhinitisType);

    // 更新鼻炎类型
    int updateRhinitisType(RhinitisType rhinitisType);

    // 删除鼻炎类型(逻辑删除)
    int deleteRhinitisType(Long rid);

}