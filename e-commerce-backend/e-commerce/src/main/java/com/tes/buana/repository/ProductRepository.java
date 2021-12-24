package com.tes.buana.repository;

import com.tes.buana.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p INNER JOIN p.productCategory pc" +
            " WHERE (LOWER(pc.name) LIKE CONCAT('%',LOWER(:category),'%') AND LOWER(p.name) LIKE CONCAT('%',LOWER(:searchKeyword),'%'))" +
            " AND p.markForDelete = false")
    Page<Product> findAllByMarkForDeleteIsFalse(String category, String searchKeyword, Pageable pageable);
    Optional<Product> findByIdAndMarkForDeleteIsFalse(String id);
}
