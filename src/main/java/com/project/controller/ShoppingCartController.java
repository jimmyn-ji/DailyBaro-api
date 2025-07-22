package com.project.controller;

import com.project.model.dto.AddToCartDTO;
import com.project.model.dto.UpdateCartItemDTO;
import com.project.model.vo.ShoppingCartItemVO;
import com.project.service.ShoppingCartService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping("/addToCart/{productId}")
    public Result<ShoppingCartItemVO> addToCart(@PathVariable Long productId, @RequestBody AddToCartDTO addToCartDTO) {
        // 将路径参数注入DTO
        addToCartDTO.setProductId(productId);
        return shoppingCartService.addToCart(addToCartDTO);
    }

    @RequestMapping("/getCartItems")
    public Result<List<ShoppingCartItemVO>> getCartItems() {
        return shoppingCartService.getCartItems();
    }

    @RequestMapping("/getCartItemsByUid/{userId}")
    public Result<List<ShoppingCartItemVO>> getCartItemsByUid(@PathVariable Long userId) {
        return shoppingCartService.getCartItemsByUid(userId);
    }

    @PostMapping("/updateCartItem")
    public Result<ShoppingCartItemVO> updateCartItem(@RequestBody UpdateCartItemDTO updateCartItemDTO) {
        return shoppingCartService.updateCartItem(updateCartItemDTO);
    }

    @RequestMapping("/deleteCartItem/{itemId}")
    public Result<Void> deleteCartItem(@PathVariable Long itemId) {
        return shoppingCartService.deleteCartItem(itemId);
    }
}