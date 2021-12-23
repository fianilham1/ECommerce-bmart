package com.tes.buana.service.impl;

import com.tes.buana.common.exception.NotEnoughProductException;
import com.tes.buana.common.exception.NotFoundException;
import com.tes.buana.entity.Cart;
import com.tes.buana.entity.CartItems;
import com.tes.buana.entity.Product;
import com.tes.buana.entity.Users;
import com.tes.buana.repository.CartItemsRepository;
import com.tes.buana.repository.CartRepository;
import com.tes.buana.repository.ProductRepository;
import com.tes.buana.service.CartService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @SneakyThrows
    @Transactional
    @Override
    public Cart addItemToCart(CartItems cartItems, Users users, String options){
        Cart existingCart = cartRepository.findByUsersAndMarkForDeleteIsFalse(users).orElseThrow(() -> new NotFoundException("Cart is not found"));

        //get new cart items with existing product data
        CartItems newCartItems = getNewCartItems(cartItems,existingCart,options);

        Double sumProductsPrice = existingCart.getCartItemsList().stream().mapToDouble(CartItems::getProductPrice).sum();
        existingCart.setTotalProductsPrice(sumProductsPrice);

        //save the New Cart Items
        cartItemsRepository.save(newCartItems);
        return existingCart;
    }

    @SneakyThrows
    @Transactional
    @Override
    public void deleteItemFromCart(String cartItemsProductId, Users users){
        Cart existingCart = cartRepository.findByUsersAndMarkForDeleteIsFalse(users)
                .orElseThrow(() -> new NotFoundException("Cart is not found"));
        Product existingProduct = productRepository.findByIdAndMarkForDeleteIsFalse(cartItemsProductId)
                .orElseThrow(() -> new NotFoundException("product is not found"));
        CartItems existingCartItems = cartItemsRepository.findByProductAndCartAndMarkForDeleteIsFalse(existingProduct,existingCart)
                .orElseThrow(() -> new NotFoundException("Cart Item is not found"));
        existingCartItems.setMarkForDelete(true);
        existingCartItems.setDeletedDate(new Date());
        existingCart.setTotalProductsPrice(
                existingCart.getTotalProductsPrice()-existingCartItems.getProductPrice()
        );
        cartRepository.save(existingCart);
    }

    @SneakyThrows
    @Transactional
    public CartItems getNewCartItems(CartItems requestedCartItems, Cart existingCart, String options){
        List<Product> productList = productRepository.findAllByMarkForDeleteIsFalse("","",null).stream().collect(Collectors.toList());
        //get product in stock and check its quantity in stock
        Product product = productList.stream()
                .filter(p -> p.getId().equals(requestedCartItems.getProduct().getId()))
                .findFirst().orElseThrow(() -> new NotFoundException("product id is not found"));

        Optional<CartItems> existingCartItem= cartItemsRepository.findByProductAndCartAndMarkForDeleteIsFalse(product,existingCart);
        if(existingCartItem.isPresent()){
            CartItems cartItems = existingCartItem.get();
            if(options=="addQty"){
                //update the quantity by adding with previous value
                cartItems.setProductQuantity(cartItems.getProductQuantity()+requestedCartItems.getProductQuantity());
                cartItems.setProductPrice(cartItems.getProduct().getPrice()*cartItems.getProductQuantity());
            }else{
                //update the quantity by replace it with new value
                cartItems.setProductQuantity(requestedCartItems.getProductQuantity());
                cartItems.setProductPrice(cartItems.getProduct().getPrice()*cartItems.getProductQuantity());
            }
            //check the stock
            checkProductQuantityInStock(product,cartItems.getProductQuantity());
            return cartItems;
        }

        requestedCartItems.setProduct(product);
        requestedCartItems.setCart(existingCart);
        requestedCartItems.setProductPrice(requestedCartItems.getProductQuantity()*product.getPrice());
        existingCart.getCartItemsList().add(requestedCartItems);

        //check the stock
        checkProductQuantityInStock(product,requestedCartItems.getProductQuantity());
        return requestedCartItems;
    }

    @SneakyThrows
    public void checkProductQuantityInStock(Product product, Integer quantityRequest){
        if(product.getQuantityInStock()-quantityRequest<0){
            throw new NotEnoughProductException(product);
        }
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    @Override
    public Cart getCartCustomer(Users users){
        return cartRepository.findByUsersAndMarkForDeleteIsFalse(users).orElseThrow(() -> new NotFoundException("cart is not found"));
    }

}
