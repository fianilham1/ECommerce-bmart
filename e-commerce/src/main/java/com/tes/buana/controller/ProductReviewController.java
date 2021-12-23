package com.tes.buana.controller;

import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.tes.buana.common.converter.ProductCategoryConverter;
import com.tes.buana.common.converter.ProductReviewConverter;
import com.tes.buana.config.JwtUtil;
import com.tes.buana.dto.product_category.ProductCategoryDto;
import com.tes.buana.dto.product_category.ProductCategoryListDto;
import com.tes.buana.dto.product_review.ProductReviewDto;
import com.tes.buana.entity.ProductCategory;
import com.tes.buana.entity.ProductReview;
import com.tes.buana.entity.web_query.ProductCategoryParamsQuery;
import com.tes.buana.service.ProductCategoryService;
import com.tes.buana.service.ProductReviewService;
import com.tes.buana.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductReviewController extends BaseController{

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ProductReviewConverter converter;

    /**
     * ROLE_CUSTOMER -----------------
     * */

    @PostMapping("/review")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Response<ProductReviewDto> addProductReview(@RequestBody ProductReviewDto productReviewDto){
        try {
            ProductReview productReview = productReviewService.addProductReview(converter.toEntity(productReviewDto),productReviewDto.getProductId(),userService.getUser(getUsername()));
            return ResponseHelper.ok(converter.toDto(productReview));
        } catch (Throwable e) {
            log.error("error create product categories : ",e);
            return (Response<ProductReviewDto>) showResponseError(e);
        }
    }


}
