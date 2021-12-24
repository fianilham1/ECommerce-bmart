package com.tes.buana.dto.cart;

import com.tes.buana.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private UserDto users;
    private List<CartItemsDto> cartItemsList;
    private Integer totalProducts;
    private Double totalProductsPrice;
}
