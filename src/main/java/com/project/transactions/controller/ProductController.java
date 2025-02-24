package com.project.transactions.controller;

import com.project.transactions.model.dto.*;
import com.project.transactions.model.vo.ProductDetailInfoVO;
import com.project.transactions.model.vo.ProductInfoVO;
import com.project.transactions.model.vo.ProductOutInfoVO;
import com.project.transactions.model.vo.ProductTypeInfoVO;
import com.project.transactions.result.ResultData;
import com.project.transactions.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    //添加商品类别
    @RequestMapping("/insertProductType")
    public ResultData<ProductTypeInfoVO> insertProductType(@RequestBody InsertProductTypeDTO insertProductTypeDTO) {
        return productService.insertProductType(insertProductTypeDTO);
    }
    //删除商品类别
    @RequestMapping("/deleteProductType")
    public ResultData<ProductTypeInfoVO> deleteProductType(@RequestBody PtidDTO ptidDTO) {
        return productService.deleteProductType(ptidDTO);
    }
    //查询商品类别
    @PostMapping("/selectProductType")
    public ResultData<List<ProductTypeInfoVO>> selectProductType(@RequestBody QueryInfoDTO queryInfoDTO) {
        return productService.selectProductType(queryInfoDTO);
    }
    //修改商品类别
    @RequestMapping("/updateProductType")
    public ResultData<ProductTypeInfoVO> updateProductType(@RequestBody InsertProductTypeDTO insertProductTypeDTO) {
        return productService.updateProductType(insertProductTypeDTO);
    }

    //上传商品图片
    @PostMapping("/uploadProductImage")
    public ResultData<String> uploadProductImage(@RequestParam("file") MultipartFile file) {
        return productService.uploadProductImage(file);
    }
    //添加商品
    @PostMapping("/insertProduct")
    public ResultData<ProductInfoVO> insertProduct(@RequestBody InsertProductDTO insertProductDTO) {
        return productService.insertProduct(insertProductDTO);
    }

    //上架商品
    @RequestMapping("/upProduct")
    public ResultData<ProductInfoVO> upProduct(@RequestBody UpProductDTO upProductDTO) {
        return productService.upProduct(upProductDTO);
    }

    //管理员查询商品——上架/下架
    @RequestMapping("/selectProductStatueByUser")
    public ResultData<List<ProductInfoVO>> selectProductStatueByUser(@RequestBody QueryInfoDTO queryInfoDTO){
        return productService.selectProductStatueByUser(queryInfoDTO);
    }
    //查询商品
    @RequestMapping("/selectProductList")
    public ResultData<ProductOutInfoVO> selectProductList(@RequestBody QueryInfoDTO queryInfoDTO) {
        return productService.selectProductList(queryInfoDTO);
    }
    //商品详情
    @RequestMapping("/selectProductDetail")
    public ResultData<ProductDetailInfoVO> selectProductDetail(@RequestBody PidDTO pidDTO) {
        return productService.selectProductDetail(pidDTO);
    }

    //下架商品
    @RequestMapping("/downProduct")
    public ResultData<ProductInfoVO> downProduct(@RequestBody DownProductDTO downProductDTO) {
        return productService.downProduct(downProductDTO);
    }

}
