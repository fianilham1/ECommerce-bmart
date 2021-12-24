package com.tes.buana.service;

import com.tes.buana.entity.ProductCategory;
import com.tes.buana.entity.web_query.ProductCategoryParamsQuery;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> addProductCategories(List<ProductCategory> productCategoryList);

    Page<ProductCategory> getAllProductCategory(ProductCategoryParamsQuery productCategoryParamsQuery);

    ProductCategory getProductCategory(String id);

    void deleteProductCategory (String id);

    ProductCategory updateProductCategory (ProductCategory updatedProductCategory);

}
