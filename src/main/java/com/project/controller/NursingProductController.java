package com.project.controller;

import com.github.pagehelper.PageInfo;
import com.project.model.NursingProduct;
import com.project.model.dto.NursingProductDTO;
import com.project.model.vo.NursingProductVO;
import com.project.service.NursingProductService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class NursingProductController {

    @Autowired
    private NursingProductService nursingProductService;

    @RequestMapping("/getProductByPage")
    public Result<PageInfo<NursingProduct>> getProductByPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<NursingProduct> pageInfo = nursingProductService.getProductByPage(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @RequestMapping("/getProductById/{productId}")
    public Result<NursingProduct> getProductById(@PathVariable Integer productId) {
        NursingProduct product = nursingProductService.getProductById(productId);
        return Result.success(product);
    }

    @RequestMapping(value = "/add")
    public Result<NursingProductVO> addProduct(NursingProductDTO productDTO) {
        return nursingProductService.addProduct(productDTO);
    }

    @RequestMapping("/update")
    public Result<NursingProductVO> updateProduct(NursingProductDTO productDTO) {
        NursingProductVO vo = nursingProductService.updateProduct(productDTO).getData();
        return Result.success(vo);
    }

    @DeleteMapping("/delete/{productId}")
    public Result<Void> deleteProduct(@PathVariable Integer productId) {
        nursingProductService.deleteProduct(productId);
        return Result.success();
    }
}