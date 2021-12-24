package com.tes.buana.controller;

import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.tes.buana.common.converter.ProductCategoryConverter;
import com.tes.buana.config.JwtUtil;
import com.tes.buana.dto.DeleteResponseDto;
import com.tes.buana.dto.product_category.ProductCategoryDto;
import com.tes.buana.dto.product_category.ProductCategoryListDto;
import com.tes.buana.entity.ProductCategory;
import com.tes.buana.entity.web_query.ProductCategoryParamsQuery;
import com.tes.buana.service.ProductCategoryService;
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
@RequestMapping("/product-category")
public class ProductCategoryController extends BaseController{

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ProductCategoryConverter converter;

    /**
     * ROLE_ADMIN -----------------
     * */

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<List<ProductCategoryDto>> addAllProductCategory(@RequestBody ProductCategoryListDto productCategoryListRequest){
        try {
            List<ProductCategory> productCategories = productCategoryService.addProductCategories(productCategoryListRequest.getProductCategories().stream()
            .map(converter::toEntity)
            .collect(Collectors.toList()));
            return ResponseHelper.ok(productCategories.stream()
            .map(converter::toDto)
            .collect(Collectors.toList()));
        } catch (Throwable e) {
            log.error("error create product categories : ",e);
            return (Response<List<ProductCategoryDto>>) showResponseError(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<DeleteResponseDto> deleteProductCategory(@PathVariable("id") String productCategoryId) {
        try {
            productCategoryService.deleteProductCategory(productCategoryId);
            return ResponseHelper.ok(DeleteResponseDto.builder().deleteMessage("delete product category with id "+productCategoryId+" is success").build());
        } catch (Throwable e) {
            log.error("error delete product categories with id {} : {}",productCategoryId,e);
            return (Response<DeleteResponseDto>) showResponseError(e);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<ProductCategoryDto> updateProductCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        try {
            ProductCategory productCategory = productCategoryService.updateProductCategory(converter.toEntity(productCategoryDto));
            return ResponseHelper.ok(converter.toDto(productCategory));
        } catch (Throwable e) {
            log.error("error update product categories with id {} : {}",productCategoryDto.getId(),e);
            return (Response<ProductCategoryDto>) showResponseError(e);
        }
    }


    /**
     * PUBLIC -----------------
     * */

    @GetMapping
    public Response<Page<ProductCategoryDto>> getAllProductCategory(ProductCategoryParamsQuery productCategoryParamsQuery) {
        try {
            Page<ProductCategoryDto> productCategories = productCategoryService.getAllProductCategory(productCategoryParamsQuery)
                    .map(converter::toDto);
            return ResponseHelper.ok(productCategories);
        } catch (Throwable e) {
            log.error("error get all product categories : ",e);
            return (Response<Page<ProductCategoryDto>>) showResponseError(e);
        }
    }

    @GetMapping("/{id}")
    public Response<ProductCategoryDto> getProductCategory(@PathVariable("id") String productCategoryId) {
        try {
            ProductCategory productCategory = productCategoryService.getProductCategory(productCategoryId);
            return ResponseHelper.ok(converter.toDto(productCategory));
        } catch (Throwable e) {
            log.error("error get product categories with id {} : {}",productCategoryId,e);
            return (Response<ProductCategoryDto>) showResponseError(e);
        }
    }


}
