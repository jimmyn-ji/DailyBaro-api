package com.project.transactions.service.impl;

import com.project.transactions.exception.CustomException;
import com.project.transactions.mapper.ProductMapper;
import com.project.transactions.mapper.ProductTypeMapper;
import com.project.transactions.mapper.UserMapper;
import com.project.transactions.model.Product;
import com.project.transactions.model.ProductType;
import com.project.transactions.model.User;
import com.project.transactions.model.dto.*;
import com.project.transactions.model.vo.ProductDetailInfoVO;
import com.project.transactions.model.vo.ProductInfoVO;
import com.project.transactions.model.vo.ProductOutInfoVO;
import com.project.transactions.model.vo.ProductTypeInfoVO;
import com.project.transactions.result.ResultData;
import com.project.transactions.result.ResultUtil;
import com.project.transactions.service.ProductService;
import com.project.transactions.util.CollectionUtils;
import com.project.transactions.util.SnowFlake;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private SnowFlake snowflake;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ResultData<ProductTypeInfoVO> insertProductType(InsertProductTypeDTO insertProductTypeDTO) {
        //获取商品类别名称
        String productTypeName = insertProductTypeDTO.getProductTypeName();
        //获取商品类别id
        Long ptId = insertProductTypeDTO.getPtId();

        if(StringUtils.isEmpty(productTypeName)){
            throw new RuntimeException("商品类别不能为空");
        }
        //查询是否重复
        Example example = new Example(ProductType.class);
        example.createCriteria().andEqualTo("isdelete", 0)
        .andEqualTo("productTypeName", productTypeName);
        List<ProductType> productTypeList = productTypeMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(productTypeList)){
            throw new CustomException("商品类别已存在");
        }

        //插入商品类别
        ProductType productType = new ProductType();
        BeanUtils.copyProperties(insertProductTypeDTO, productType);
        productType.setPtId(snowflake.nextId());
        productTypeMapper.insertSelective(productType);
        
        //返回商品类别信息
        ProductTypeInfoVO productTypeInfoVO = new ProductTypeInfoVO();
        BeanUtils.copyProperties(productType, productTypeInfoVO);
        return ResultUtil.getResultData(productTypeInfoVO);
    }
    //删除商品类别
    @Override
    public ResultData<ProductTypeInfoVO> deleteProductType(PtidDTO ptidDTO) {
        //获取商品类别id
        Long ptId = ptidDTO.getPtId();
        //获取商品类别
        ProductType productType = productTypeMapper.selectByPrimaryKey(ptId);
        productType.setIsdelete(1);
        //删除商品类别
        productTypeMapper.updateByPrimaryKeySelective(productType);
        //返回商品类别信息
        ProductTypeInfoVO productTypeInfoVO = new ProductTypeInfoVO();
        BeanUtils.copyProperties(productType, productTypeInfoVO);
        return ResultUtil.getResultData(productTypeInfoVO);
    }
    //查询商品类别
    @Override
    public ResultData<List<ProductTypeInfoVO>> selectProductType(QueryInfoDTO queryInfoDTO) {
        //获取查询内容
        String queryContent = queryInfoDTO.getQueryContent();
        //获取查询条件
        Example productTypeExample = new Example(ProductType.class);
        Example.Criteria criteria = productTypeExample.createCriteria();

        criteria.andEqualTo("isdelete", 0);

        //如果有传如查询内容
        if(StringUtils.isNotEmpty(queryContent)){
            criteria.andLike("productTypeName", "%" + queryContent + "%");
        }

        List<ProductType> productTypeList = productTypeMapper.selectByExample(productTypeExample);
        //返回商品类别信息
        List<ProductTypeInfoVO> productTypeInfoVOList = productTypeList.stream().map(productType -> {
            ProductTypeInfoVO productTypeInfoVO = new ProductTypeInfoVO();
            BeanUtils.copyProperties(productType, productTypeInfoVO);
            return productTypeInfoVO;
        }).collect(Collectors.toList());
        return ResultUtil.getResultData(productTypeInfoVOList);
    }
    //修改商品类别
    @Override
    public ResultData<ProductTypeInfoVO> updateProductType(InsertProductTypeDTO insertProductTypeDTO) {
        //获取商品类别id
        Long ptId = insertProductTypeDTO.getPtId();
        //获取商品类别名称
        String productTypeName = insertProductTypeDTO.getProductTypeName();

        //获取商品类别信息
        Example productTypeExample = new Example(ProductType.class);
        productTypeExample.createCriteria().andEqualTo("isdelete", 0)
        .andEqualTo("productTypeName", productTypeName);
        List<ProductType> productTypeList = productTypeMapper.selectByExample(productTypeExample);
        if(CollectionUtils.isNotEmpty(productTypeList)){
            throw new RuntimeException("商品类别已存在");
        }
        //更新商品类别
        ProductType productType = productTypeMapper.selectByPrimaryKey(ptId);
        productType.setProductTypeName(productTypeName);
        productTypeMapper.updateByPrimaryKeySelective(productType);
        //返回商品类别信息
        ProductTypeInfoVO productTypeInfoVO = new ProductTypeInfoVO();
        BeanUtils.copyProperties(productType, productTypeInfoVO);
        return ResultUtil.getResultData(productTypeInfoVO);
    }
    //上传商品图片
    @Override
    public ResultData<String> uploadProductImage(MultipartFile file) {
        try {
            // 获取原始文件名并提取扩展名
            String originalFileName = file.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            // 使用Paths构建规范路径
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);
            
            String newFileName = UUID.randomUUID() + extension;
            Path targetLocation = uploadPath.resolve(newFileName);
            
            file.transferTo(targetLocation);
            
            // 返回相对路径
            String imagePath = "/uploads/" + newFileName;
            return ResultUtil.getResultData(imagePath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getResultData("上传失败");
        }
    }
    @Override
    public ResultData<ProductInfoVO> insertProduct(InsertProductDTO insertProductDTO) {
        //获取商品发布者
        Long uid = insertProductDTO.getUid();
        //获取商品名称
        String productName = insertProductDTO.getProductName();
        //获取商品价格
        BigDecimal price = insertProductDTO.getPrice();
        //获取商品描述
        String productDescription = insertProductDTO.getProductDescription();
        //获取商品分类
        Long ptId = insertProductDTO.getPtId();
        //获取商品图片
        String productImage = insertProductDTO.getImageName();
        //获取商品库存
        String imagePath = insertProductDTO.getImagePath();

        //获取商品分类
        Example productTypeExample = new Example(ProductType.class);
        productTypeExample.createCriteria().andEqualTo("isdelete",0)
        .andEqualTo("ptId", ptId);
        List<ProductType> productTypeList = productTypeMapper.selectByExample(productTypeExample);
        //商品分类Map stream流
        Map<Long, String> productTypeMap = productTypeList.stream().collect(Collectors.toMap(ProductType::getPtId, ProductType::getProductTypeName));
       
    
        //插入数据
        Product insertProduct = new Product();
        BeanUtils.copyProperties(insertProductDTO, insertProduct);
        insertProduct.setPid(snowflake.nextId());
        //0未上架 1上架
        insertProduct.setStatus(0);
        productMapper.insertSelective(insertProduct);

        //返回商品信息
        ProductInfoVO productInfoVO = new ProductInfoVO();
        BeanUtils.copyProperties(insertProduct, productInfoVO);
        productInfoVO.setProductTypeName(productTypeMap.get(ptId));

        return ResultUtil.getResultData(productInfoVO);
    }

    @Override
    public ResultData<ProductInfoVO> upProduct(UpProductDTO upProductDTO) {
        //获取商品id
        Long pid = upProductDTO.getPid();
        //获取商品状态
        Integer status = upProductDTO.getStatus();

        //获取商品
        Product product = productMapper.selectByPrimaryKey(pid);
        //更新商品状态
        product.setStatus(1);
        productMapper.updateByPrimaryKeySelective(product);
        //返回商品信息
        ProductInfoVO productInfoVO = new ProductInfoVO();
        BeanUtils.copyProperties(product, productInfoVO);
        return ResultUtil.getResultData(productInfoVO);
    }

    //用户查询商品上下架状态
    @Override
    public ResultData<List<ProductInfoVO>> selectProductStatueByUser(QueryInfoDTO queryInfoDTO) {
        // 获取查询内容
        String queryContent = queryInfoDTO.getQueryContent();
        // 获取用户id
        Long uid = queryInfoDTO.getUid();
        
        // 创建查询条件
        Example productExample = new Example(Product.class);
        Example.Criteria criteria = productExample.createCriteria().andEqualTo("isdelete", 0)
            .andEqualTo("uid", uid);

        //获取product中的pid
        List<Long> pids = productMapper.selectByExample(productExample).stream().map(Product::getPid).collect(Collectors.toList());
        //获取product中的ptId
        List<Long> ptIds = productMapper.selectByExample(productExample).stream().map(Product::getPtId).collect(Collectors.toList());
        // 如果有传入查询内容，则加上模糊查询条件
        if (StringUtils.isNotEmpty(queryContent)) {
            criteria.andLike("productName", "%" + queryContent + "%");
        }
        // 执行查询
        List<Product> productList = productMapper.selectByExample(productExample);
        // 查询所有商品分类
        List<ProductType> productTypes = productTypeMapper.selectAll();
        Map<Long, String> productTypeMap = productTypes.stream()
                .collect(Collectors.toMap(ProductType::getPtId, ProductType::getProductTypeName));
        
        Example userExample = new Example(User.class);
        userExample.createCriteria().andIn("uid", Collections.singleton(uid));
        List<User> userList = userMapper.selectByExample(userExample);
        Map<Long, String> userMap = userList.stream().collect(Collectors.toMap(User::getUid, User::getAccount));
        
        // 将查询结果转换为 VO 对象列表
        List<ProductInfoVO> productInfoVOList = productList.stream().map(product -> {
            ProductInfoVO productInfoVO = new ProductInfoVO();
            BeanUtils.copyProperties(product, productInfoVO);
            productInfoVO.setProductTypeName(productTypeMap.get(product.getPtId()));
            productInfoVO.setUid(product.getUid());
            //获取用户账号
            productInfoVO.setAccount(userMap.get(product.getUid()));

            return productInfoVO;
        }).collect(Collectors.toList());

        // 返回结果
        return ResultUtil.getResultData(productInfoVOList);
    }

    @Override
    public ResultData<ProductOutInfoVO> selectProductList(QueryInfoDTO queryInfoDTO) {
        // 获取查询内容
        String queryContent = queryInfoDTO.getQueryContent();

        // 创建查询条件
        Example productExample = new Example(Product.class);
        Example.Criteria criteria = productExample.createCriteria().andEqualTo("isdelete", 0)
                .andEqualTo("status", 1);

        // 如果有传入查询内容，则加上模糊查询条件
        if (StringUtils.isNotBlank(queryContent)) {
            criteria.andLike("productName", "%" + queryContent + "%");
        }

        // 执行查询 - 这里只查询一次
        List<Product> productList = productMapper.selectByExample(productExample);

        if (CollectionUtils.isEmpty(productList)) {
            return ResultUtil.getResultData(new ProductOutInfoVO());
        }

        // 获取所有商品分类信息
        List<ProductType> productTypes = productTypeMapper.selectAll();
        Map<Long, String> productTypeMap = productTypes.stream()
                .collect(Collectors.toMap(ProductType::getPtId, ProductType::getProductTypeName));
        //获取s所有ptId
        Set<String> productTypeNames = productTypes.stream().map(ProductType::getProductTypeName).collect(Collectors.toSet());
        
        // 获取所有相关的用户
        List<Long> uids = productList.stream().map(Product::getUid).collect(Collectors.toList());
        Example userExample = new Example(User.class);
        userExample.createCriteria().andIn("uid", uids);
        List<User> userList = userMapper.selectByExample(userExample);
        Map<Long, String> userMap = userList.stream().collect(Collectors.toMap(User::getUid, User::getAccount));

        //productInfoVOList
        List<ProductInfoVO> productInfoVOList = productList.stream().map(product -> {
            ProductInfoVO productInfoVO = new ProductInfoVO();
            BeanUtils.copyProperties(product, productInfoVO);
            productInfoVO.setProductTypeName(productTypeMap.get(product.getPtId()));
            productInfoVO.setUid(product.getUid());
            productInfoVO.setAccount(userMap.get(product.getUid()));
            return productInfoVO;
        }).collect(Collectors.toList());

        ProductOutInfoVO productOutInfoVO = new ProductOutInfoVO();
        productOutInfoVO.setProductInfoVOS(productInfoVOList);
        productOutInfoVO.setProductTypeNames(productTypeNames);
        
        return ResultUtil.getResultData(productOutInfoVO);
    }

    @Override
    public ResultData<ProductDetailInfoVO> selectProductDetail(PidDTO pidDTO) {
        //获取商品id
        Long pid = pidDTO.getPid();
        //获取商品
        Product product = productMapper.selectByPrimaryKey(pid);

        //获取发布者
        Long uid = product.getUid();
        //获取发布者
        User user = userMapper.selectByPrimaryKey(uid);
        //获取发布者账号
        String account = user.getAccount();
        //获取商品类别


        Long ptId = product.getPtId();
        //获取商品类别
        ProductType productType = productTypeMapper.selectByPrimaryKey(ptId);
        //获取商品类别名称
        String productTypeName = productType.getProductTypeName();
        //返回商品信息
        ProductDetailInfoVO productDetailInfoVO = new ProductDetailInfoVO();
        BeanUtils.copyProperties(product, productDetailInfoVO);
        productDetailInfoVO.setAccount(account);
        productDetailInfoVO.setProductTypeName(productTypeName);

        //获取商品评论
        // List<Comment> commentList = commentMapper.selectByPid(pid);
        // if(CollectionUtil.isNotEmpty(commentList))
        // {
        //     productDetailInfoVO.setCommentList(commentList);
        // }
       
        //返回商品评论
        // productDetailInfoVO.setCommentList(commentList);
        // return ResultUtil.getResultData(productDetailInfoVO);
        return ResultUtil.getResultData(productDetailInfoVO);

    }

    @Override
    public ResultData<ProductInfoVO> downProduct(DownProductDTO downProductDTO) {
        //获取商品id
        Long pid = downProductDTO.getPid();
        //获取商品状态
        Integer status = downProductDTO.getStatus();
        //获取商品
        Product product = productMapper.selectByPrimaryKey(pid);
        //更新商品状态
        product.setStatus(0);
        productMapper.updateByPrimaryKeySelective(product);
        //返回商品信息
        ProductInfoVO productInfoVO = new ProductInfoVO();
        BeanUtils.copyProperties(product, productInfoVO);
        return ResultUtil.getResultData(productInfoVO);
    }
}
