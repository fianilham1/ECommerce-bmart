package com.tes.buana.dto.product_review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDto {

    String id;

    Date createdDate;

    String productId;

    String userName;

    String userImage;

    Double rating;

    String comment;
}
