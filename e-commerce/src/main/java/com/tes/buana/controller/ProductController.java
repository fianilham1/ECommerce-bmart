package com.tes.buana.controller;

import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.tes.buana.common.converter.ProductConverter;
import com.tes.buana.dto.product.ProductDto;
import com.tes.buana.dto.product.ProductListDto;
import com.tes.buana.entity.Product;
import com.tes.buana.entity.web_query.ProductParamsQuery;
import com.tes.buana.service.ProductService;
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
public class ProductController extends BaseController{

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConverter converter;

    /**
     ROLE_ADMIN ---------------
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<List<ProductDto>> addProduct(@RequestBody ProductListDto productListRequest){
        try {
            List<Product> productList = productService.addProducts(productListRequest.getProductList().stream()
                   .map(converter::toEntity)
                   .collect(Collectors.toList()));
            return ResponseHelper.ok(productList.stream()
                    .map(converter::toDto)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("error create products : ",e);
            return (Response<List<ProductDto>>) showResponseError(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<String> deleteProduct(@PathVariable("id") String productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseHelper.ok("delete with id "+productId+" is success");
        } catch (Throwable e) {
            log.error("error delete product with id {} : {}",productId,e);
            return (Response<String>) showResponseError(e);
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<ProductDto> updateProduct(@RequestBody ProductDto updatedProductRequest) {
        try {
            Product product = productService.updateProduct(converter.toEntity(updatedProductRequest));
            return ResponseHelper.ok(converter.toDto(product));
        } catch (Throwable e) {
            log.error("error update product with id {} : {}",updatedProductRequest.getId(),e);
            return (Response<ProductDto>) showResponseError(e);
        }
    }

    @PutMapping("/image")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<ProductDto> updateProductImage(@RequestBody ProductDto updatedProductRequest) {
        try {
            Product product = productService.updateProductImage(converter.toEntity(updatedProductRequest));
            return ResponseHelper.ok(converter.toDto(product));
        } catch (Throwable e) {
            log.error("error update product with id {} : {}",updatedProductRequest.getId(),e);
            return (Response<ProductDto>) showResponseError(e);
        }
    }

    /**
     PUBLIC --------------
     */

    @GetMapping
    public Response<Page<ProductDto>> getAllProduct(ProductParamsQuery productParamsQuery) {
        try {
            Page<ProductDto> productList = productService.getAllProduct(productParamsQuery)
                    .map(converter::toDto);
            return ResponseHelper.ok(productList);
        } catch (Throwable e) {
            log.error("error get all products : ",e);
            return (Response<Page<ProductDto>>) showResponseError(e);
        }
    }

    @GetMapping("/{id}")
    public Response<ProductDto> getProduct(@PathVariable("id") String productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseHelper.ok(converter.toDto(product));
        } catch (Throwable e) {
            log.error("error get product with id {} : {}",productId,e);
            return (Response<ProductDto>) showResponseError(e);
        }
    }

}
