package com.tes.buana.controller;

import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.tes.buana.common.converter.CartConverter;
import com.tes.buana.common.converter.CartItemsConverter;
import com.tes.buana.dto.DeleteResponseDto;
import com.tes.buana.dto.cart.CartDto;
import com.tes.buana.dto.cart.CartItemsDto;
import com.tes.buana.entity.Cart;
import com.tes.buana.service.CartService;
import com.tes.buana.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController extends BaseController{

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartConverter cartConverter;

    @Autowired
    private CartItemsConverter cartItemsConverter;

    /**
     ROLE_CUSTOMER ---------------
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Response<CartDto> addCartItem(@RequestBody CartItemsDto cartItemsDto){
        try {
            Cart cart = cartService.addItemToCart(cartItemsConverter.toEntity(cartItemsDto),userService.getUser(getUsername()),"addQty");
            return ResponseHelper.ok(cartConverter.toDto(cart));
        } catch (Throwable e) {
            log.error("error add cart item : ",e);
            return (Response<CartDto>) showResponseError(e);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Response<CartDto> updateCartItem(@RequestBody CartItemsDto cartItemsDto){
        try {
            Cart cart = cartService.addItemToCart(cartItemsConverter.toEntity(cartItemsDto),userService.getUser(getUsername()),"replaceQty");
            return ResponseHelper.ok(cartConverter.toDto(cart));
        } catch (Throwable e) {
            log.error("error add cart item : ",e);
            return (Response<CartDto>) showResponseError(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Response<DeleteResponseDto> deleteItemCart(@PathVariable("id") String cartItemsProductId) {
        try {
            cartService.deleteItemFromCart(cartItemsProductId,userService.getUser(getUsername()));
            return ResponseHelper.ok(DeleteResponseDto.builder().deleteMessage("delete cart item product with id "+cartItemsProductId+" is success").build());
        } catch (Throwable e) {
            log.error("error delete cart item product with id {} : {}",cartItemsProductId,e);
            return (Response<DeleteResponseDto>) showResponseError(e);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Response<CartDto> getItemCart() {
        try {
            Cart cart = cartService.getCartCustomer(userService.getUser(getUsername()));
            return ResponseHelper.ok(cartConverter.toDto(cart));
        } catch (Throwable e) {
            log.error("error get cart item : ",e);
            return (Response<CartDto>) showResponseError(e);
        }
    }


}
