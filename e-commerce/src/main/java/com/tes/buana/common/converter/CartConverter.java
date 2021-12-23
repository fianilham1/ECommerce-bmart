package com.tes.buana.common.converter;

import com.tes.buana.common.util.MapperUtil;
import com.tes.buana.dto.cart.CartDto;
import com.tes.buana.entity.Cart;
import org.springframework.stereotype.Service;

@Service
public class CartConverter {
    public CartDto toDto(Cart cart){
        CartDto cartDto = MapperUtil.parse(cart, CartDto.class);
        cartDto.setTotalProducts(cartDto.getCartItemsList().size());
        return cartDto;
    }

    public Cart toEntity(CartDto cartDto){
        return MapperUtil.parse(cartDto, Cart.class);
    }
}

