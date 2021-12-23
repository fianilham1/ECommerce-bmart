package com.tes.buana.repository;

import com.tes.buana.entity.Cart;
import com.tes.buana.entity.CartItems;
import com.tes.buana.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    List<CartItems> findAllByCartAndMarkForDeleteIsFalse(Cart cart);
    Optional<CartItems> findByProductAndCartAndMarkForDeleteIsFalse(Product product, Cart cart);
    Optional<CartItems> findByIdAndCartAndMarkForDeleteIsFalse(String id, Cart cart);
}
