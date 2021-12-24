package com.tes.buana.common.converter;

import com.tes.buana.common.util.MapperUtil;
import com.tes.buana.dto.cart.CartItemsDto;
import com.tes.buana.entity.CartItems;
import org.springframework.stereotype.Service;

@Service
public class CartItemsConverter {
    public CartItemsDto toDto(CartItems cartItems){
        return MapperUtil.parse(cartItems, CartItemsDto.class);
    }

    public CartItems toEntity(CartItemsDto cartItemsDto){
        return MapperUtil.parse(cartItemsDto, CartItems.class);
    }
}

