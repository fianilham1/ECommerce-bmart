package com.tes.buana.repository;

import com.tes.buana.entity.Cart;
import com.tes.buana.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUsersAndMarkForDeleteIsFalse(Users users);
}
