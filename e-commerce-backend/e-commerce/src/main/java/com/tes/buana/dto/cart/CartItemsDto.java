package com.tes.buana.dto.cart;

import com.tes.buana.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsDto {

    private ProductDto product;

    private Integer productQuantity;

    private Double productPrice;

}
