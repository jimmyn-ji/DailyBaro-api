package com.project.mapper;

import com.project.model.ShoppingCartItem;
import com.project.model.vo.ShoppingCartItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    ShoppingCartItem selectByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    int updateQuantity(ShoppingCartItem item);

    int addToCart(ShoppingCartItem shoppingCartItem);

    List<ShoppingCartItem> getCartItems();

    List<ShoppingCartItem> getCartItemsByUid(Long uid);

    ShoppingCartItem getCartItemById(Long itemId);

    int updateCartItem(ShoppingCartItem shoppingCartItem);

    int deleteCartItem(Long itemId);


    ShoppingCartItemVO getProductInfoByProductId(@Param("productId") Long productId);



    // 批量更新购物车项的 orderId 和 isdeleted 字段
    void updateCartItems(@Param("cartItems") List<ShoppingCartItem> cartItems);

}