package com.project.controller;

import com.project.model.dto.InsertRhinitisTypeDTO;
import com.project.model.dto.QueryRhinitisDTO;
import com.project.model.dto.UpdateRhinitisTypeDTO;
import com.project.model.vo.RhinitisTypeVO;
import com.project.service.RhinitisTypeService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rhinitisType")
public class RhinitisTypeController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private RhinitisTypeService rhinitisTypeService;

    @PostMapping("/selectAllRhinitisType")
    public Result<List<RhinitisTypeVO>> selectAllRhinitisType(@RequestBody QueryRhinitisDTO queryDto) {
        return rhinitisTypeService.selectAllRhinitisType(queryDto);
    }

    @GetMapping("/getById/{rid}")
    public Result<RhinitisTypeVO> getById(@PathVariable Long rid) {
        return rhinitisTypeService.getById(rid);
    }

    @PostMapping("/insertRhinitisType")
    public Result<RhinitisTypeVO> insertRhinitisType(@ModelAttribute InsertRhinitisTypeDTO insertDto) {
        return rhinitisTypeService.insertRhinitisType(insertDto);
    }

    @PostMapping("/updateRhinitisType")
    public Result<RhinitisTypeVO> updateRhinitisType(@ModelAttribute UpdateRhinitisTypeDTO updateDto) {
        return rhinitisTypeService.updateRhinitisType(updateDto);
    }

    @DeleteMapping("/deleteRhinitisType/{rid}")
    public Result<Void> deleteRhinitisType(@PathVariable Long rid) {
        return rhinitisTypeService.deleteRhinitisType(rid);
    }
}