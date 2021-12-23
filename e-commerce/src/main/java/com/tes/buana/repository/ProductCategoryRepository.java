package com.tes.buana.repository;

import com.tes.buana.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Page<ProductCategory> findAllByMarkForDeleteIsFalse(Pageable pageable);
    Optional<ProductCategory> findByIdAndMarkForDeleteIsFalse(String id);
    Optional<ProductCategory> findByNameAndMarkForDeleteIsFalse(String name);
}
