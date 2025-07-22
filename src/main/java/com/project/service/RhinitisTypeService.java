package com.project.service;

import com.project.model.dto.InsertRhinitisTypeDTO;
import com.project.model.dto.QueryRhinitisDTO;
import com.project.model.dto.UpdateRhinitisTypeDTO;
import com.project.model.vo.RhinitisTypeVO;
import com.project.util.Result;

import java.util.List;

public interface RhinitisTypeService {

    Result<List<RhinitisTypeVO>> selectAllRhinitisType(QueryRhinitisDTO queryRhinitisDTO);

    Result<RhinitisTypeVO> getById(Long rid);

    Result<RhinitisTypeVO> insertRhinitisType(InsertRhinitisTypeDTO insertRhinitisTypeDTO);

    Result<RhinitisTypeVO> updateRhinitisType(UpdateRhinitisTypeDTO updateRhinitisTypeDTO);

    Result<Void> deleteRhinitisType(Long rid);

}