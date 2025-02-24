package com.project.transactions.service;

import com.project.transactions.model.dto.*;
import com.project.transactions.model.vo.ProductDetailInfoVO;
import com.project.transactions.model.vo.ProductInfoVO;
import com.project.transactions.model.vo.ProductOutInfoVO;
import com.project.transactions.model.vo.ProductTypeInfoVO;
import com.project.transactions.result.ResultData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    //插入商品类别
    ResultData<ProductTypeInfoVO> insertProductType(InsertProductTypeDTO insertProductTypeDTO);
    //删除商品类别
    ResultData<ProductTypeInfoVO> deleteProductType(PtidDTO ptidDTO);
    //查询商品类别
    ResultData<List<ProductTypeInfoVO>> selectProductType(QueryInfoDTO queryInfoDTO);
    //修改商品类别
    ResultData<ProductTypeInfoVO> updateProductType(InsertProductTypeDTO insertProductTypeDTO);

    //上传商品图片
    ResultData<String> uploadProductImage(MultipartFile file);

    //插入商品
    ResultData<ProductInfoVO> insertProduct(InsertProductDTO insertProductDTO);
    //审核/上架商品
    ResultData<ProductInfoVO> upProduct(UpProductDTO upProductDTO);
    //下架商品
    ResultData<ProductInfoVO> downProduct(DownProductDTO downProductDTO);
    //管理员查询商品——上架/下架
    ResultData<List<ProductInfoVO>> selectProductStatueByUser(QueryInfoDTO queryInfoDTO);
    //查询商品列表
    ResultData<ProductOutInfoVO> selectProductList(QueryInfoDTO queryInfoDTO);
    //商品详情
    ResultData<ProductDetailInfoVO> selectProductDetail(PidDTO pidDTO);

}
