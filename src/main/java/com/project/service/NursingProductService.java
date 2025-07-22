package com.project.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.controller.FileUploadController;
import com.project.mapper.NursingProductMapper;
import com.project.model.NursingProduct;
import com.project.model.dto.NursingProductDTO;
import com.project.model.vo.NursingProductVO;
import com.project.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NursingProductService {

    @Autowired
    private NursingProductMapper nursingProductMapper;

    @Autowired
    private FileUploadController fileUploadController;

    public PageInfo<NursingProduct> getProductByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<NursingProduct> products = nursingProductMapper.getAllProducts();
        return new PageInfo<>(products);
    }

    public NursingProduct getProductById(Integer productId) {
        return nursingProductMapper.getProductById(productId);
    }

    public Result<NursingProductVO> addProduct(NursingProductDTO productDTO) {
        NursingProduct product = new NursingProduct();
        BeanUtils.copyProperties(productDTO, product);

        // 处理图片上传
        if (productDTO.getImageFile() != null && !productDTO.getImageFile().isEmpty()) {
            Result<String> uploadResult = fileUploadController.uploadImage(productDTO.getImageFile());
            if (uploadResult.getCode() != 200) {
                return Result.fail(uploadResult.getMessage());
            }
            product.setImageUrl(uploadResult.getData());
        }

        nursingProductMapper.insertProduct(product);
        return Result.success(convertToVO(product));
    }

    public Result<NursingProductVO> updateProduct(NursingProductDTO productDTO) {
        NursingProduct existingProduct = nursingProductMapper.getProductById(productDTO.getId());
        if (existingProduct == null) {
            return Result.fail("护理商品不存在");
        }

        // 处理图片更新
        if (productDTO.getImageFile() != null && !productDTO.getImageFile().isEmpty()) {
            // 上传新图片
            Result<String> uploadResult = fileUploadController.uploadImage(productDTO.getImageFile());
            if (uploadResult.getCode() != 200) {
                return Result.fail(uploadResult.getMessage());
            }
            String newImageUrl = uploadResult.getData();

            productDTO.setImageUrl(newImageUrl);
        }

        BeanUtils.copyProperties(productDTO, existingProduct);
        nursingProductMapper.updateProduct(existingProduct);
        return Result.success(convertToVO(existingProduct));
    }

    public Result<Void> deleteProduct(Integer productId) {
        nursingProductMapper.deleteProduct(productId);
        return Result.success();
    }

    private NursingProductVO convertToVO(NursingProduct product) {
        NursingProductVO vo = new NursingProductVO();
        BeanUtils.copyProperties(product, vo);
        return vo;
    }
}