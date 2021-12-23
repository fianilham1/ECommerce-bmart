package com.tes.buana.service;


import com.tes.buana.entity.Cart;
import com.tes.buana.entity.CartItems;
import com.tes.buana.entity.Users;

public interface CartService {

    Cart addItemToCart(CartItems cartItems, Users users, String options);

    void deleteItemFromCart(String cartItemsId, Users users);

    Cart getCartCustomer(Users users);

}
