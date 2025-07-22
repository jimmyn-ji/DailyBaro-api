package com.project.service;

import com.project.model.dto.AddToCartDTO;
import com.project.model.dto.UpdateCartItemDTO;
import com.project.model.vo.ShoppingCartItemVO;
import com.project.util.Result;

import java.util.List;

public interface ShoppingCartService {
    Result<ShoppingCartItemVO> addToCart(AddToCartDTO addToCartDTO);

    Result<List<ShoppingCartItemVO>> getCartItems();

    Result<List<ShoppingCartItemVO>> getCartItemsByUid(Long userId);

    Result<ShoppingCartItemVO> updateCartItem(UpdateCartItemDTO updateCartItemDTO);

    Result<Void> deleteCartItem(Long itemId);
}