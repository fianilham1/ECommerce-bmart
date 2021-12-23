package com.tes.buana.service;

import com.tes.buana.entity.Product;
import com.tes.buana.entity.ProductReview;
import com.tes.buana.entity.Users;
import com.tes.buana.entity.web_query.ProductReviewParamsQuery;
import org.springframework.data.domain.Page;


public interface ProductReviewService {

    ProductReview addProductReview(ProductReview productReview, String productId, Users users);
    Page<ProductReview> getAllProductReview(ProductReviewParamsQuery productReviewParamsQuery);
    Page<ProductReview> getAllProductReviewByProduct(ProductReviewParamsQuery productReviewParamsQuery, Product product);
    Page<ProductReview> getAllProductReviewByUsers(ProductReviewParamsQuery productReviewParamsQuery, Users users);

}
