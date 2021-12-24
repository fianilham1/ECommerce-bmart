package com.tes.buana.dto.product;

import com.tes.buana.dto.product_category.ProductCategoryDto;
import com.tes.buana.dto.product_review.ProductReviewDto;
import com.tes.buana.entity.ProductReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;

    private String name;

    private String brand;

    private String code;

    private Integer quantityInStock;

    private String productSeries;

    private String size;

    private Integer netWeight;

    private Double price;

    private String image;

    private Date expDate;

    private ProductCategoryDto productCategory;

    private String description;

    private List<ProductReviewDto> productReviewList;

    private Double rating;

}
