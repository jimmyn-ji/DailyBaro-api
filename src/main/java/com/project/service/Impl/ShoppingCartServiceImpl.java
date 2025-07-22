package com.project.service.Impl;

import com.project.mapper.ShoppingCartMapper;
import com.project.model.ShoppingCartItem;
import com.project.model.dto.AddToCartDTO;
import com.project.model.dto.UpdateCartItemDTO;
import com.project.model.vo.ShoppingCartItemVO;
import com.project.service.ShoppingCartService;
import com.project.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public Result<ShoppingCartItemVO> addToCart(AddToCartDTO addToCartDTO) {
        Long userId = addToCartDTO.getUserId();
        Long productId = addToCartDTO.getProductId();

        // 查询是否已存在购物车项
        ShoppingCartItem existingItem = shoppingCartMapper.selectByUserIdAndProductId(userId, productId);

        if (existingItem != null) {
            // 存在则更新数量：原有数量 + 新增数量
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            shoppingCartMapper.updateQuantity(existingItem);
            return Result.success(convertToVO(existingItem));
        } else {
            // 不存在则新增
            ShoppingCartItem newItem = new ShoppingCartItem();
            BeanUtils.copyProperties(addToCartDTO, newItem);
            shoppingCartMapper.addToCart(newItem);
            return Result.success(convertToVO(newItem));
        }
    }

    @Override
    public Result<List<ShoppingCartItemVO>> getCartItems() {
        List<ShoppingCartItem> shoppingCartItems = shoppingCartMapper.getCartItems();
        List<ShoppingCartItemVO> vos = shoppingCartItems.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return Result.success(vos);
    }

    @Override
    public Result<List<ShoppingCartItemVO>> getCartItemsByUid(Long uid) {
        List<ShoppingCartItem> shoppingCartItems = shoppingCartMapper.getCartItemsByUid(uid);
        List<ShoppingCartItemVO> vos = shoppingCartItems.stream()
                .map(item -> {
                    ShoppingCartItemVO vo = convertToVO(item);
                    ShoppingCartItemVO productInfo = shoppingCartMapper.getProductInfoByProductId(item.getProductId());
                    if (productInfo != null) {
                        vo.setProductName(productInfo.getProductName());
                        vo.setProductImageUrl(productInfo.getProductImageUrl());
                        vo.setPrice(productInfo.getPrice()); // 添加这行，赋值价格
                    }
                    return vo;
                })
                .collect(Collectors.toList());
        return Result.success(vos);
    }

    @Override
    public Result<ShoppingCartItemVO> updateCartItem(UpdateCartItemDTO updateCartItemDTO) {
        ShoppingCartItem shoppingCartItem = shoppingCartMapper.getCartItemById(updateCartItemDTO.getItemId());
        if (shoppingCartItem == null) {
            return Result.fail("购物车项不存在");
        }

        BeanUtils.copyProperties(updateCartItemDTO, shoppingCartItem);
        shoppingCartMapper.updateQuantity(shoppingCartItem);

        return Result.success(convertToVO(shoppingCartItem));
    }

    @Override
    public Result<Void> deleteCartItem(Long itemId) {
        shoppingCartMapper.deleteCartItem(itemId);
        return Result.success();
    }

    private ShoppingCartItemVO convertToVO(ShoppingCartItem shoppingCartItem) {
        ShoppingCartItemVO vo = new ShoppingCartItemVO();
        BeanUtils.copyProperties(shoppingCartItem, vo);
        return vo;
    }
}    