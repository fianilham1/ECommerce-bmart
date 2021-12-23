package com.tes.buana.common.converter;

import com.tes.buana.common.util.MapperUtil;
import com.tes.buana.dto.product_review.ProductReviewDto;
import com.tes.buana.entity.Product;
import com.tes.buana.entity.ProductReview;
import com.tes.buana.entity.Users;
import org.springframework.stereotype.Service;

@Service
public class ProductReviewConverter {
    public ProductReviewDto toDto(ProductReview productReview){
        return ProductReviewDto.builder()
                .id(productReview.getId())
                .createdDate(productReview.getCreatedDate())
                .productId(productReview.getProduct().getId())
                .userName(productReview.getUsers().getUsername())
                .comment(productReview.getComment())
                .build();
    }

    public ProductReview toEntity(ProductReviewDto productReviewDto){
        return ProductReview.builder()
                .rating(productReviewDto.getRating())
                .comment(productReviewDto.getComment())
                .build();
    }
}

