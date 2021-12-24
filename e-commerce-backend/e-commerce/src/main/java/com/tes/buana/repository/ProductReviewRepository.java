package com.tes.buana.repository;

import com.tes.buana.entity.Product;
import com.tes.buana.entity.ProductReview;
import com.tes.buana.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    Page<ProductReview> findAllByMarkForDeleteIsFalse(Pageable pageable);
    Page<ProductReview> findAllByProductMarkForDeleteIsFalse(Pageable pageable, Product product );
    Page<ProductReview> findAllByUsersMarkForDeleteIsFalse(Pageable pageable, Users users);
    Optional<ProductReview> findByIdAndMarkForDeleteIsFalse(String id);
}
