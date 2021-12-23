package com.tes.buana.service;

import com.tes.buana.entity.Product;
import com.tes.buana.entity.web_query.ProductParamsQuery;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Product> addProducts(List<Product> productListRequest);

    Page<Product> getAllProduct(ProductParamsQuery productParamsQuery);

    Product getProductById(String id);

    void deleteProductById (String id);

    Product updateProduct (Product updatedProduct);

    Product updateProductImage (Product updatedProduct);

}
