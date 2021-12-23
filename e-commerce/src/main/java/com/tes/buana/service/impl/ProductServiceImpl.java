package com.tes.buana.service.impl;

import com.tes.buana.common.exception.NotFoundException;
import com.tes.buana.entity.Product;
import com.tes.buana.entity.ProductCategory;
import com.tes.buana.entity.web_query.ProductParamsQuery;
import com.tes.buana.repository.ProductCategoryRepository;
import com.tes.buana.repository.ProductRepository;
import com.tes.buana.service.ProductService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @SneakyThrows
    @Transactional
    @Override
    public List<Product> addProducts(List<Product> productList){
        for (Product product : productList) {
            ProductCategory productCategory = productCategoryRepository.findByIdAndMarkForDeleteIsFalse(product.getProductCategory().getId()).orElseThrow(() -> new NotFoundException("product category is not found"));
            product.setProductCategory(productCategory);
            product.setReadyStatus(true);
        }
        productRepository.saveAll(productList);
        return productList;
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    @Override
    public Page<Product> getAllProduct(ProductParamsQuery queryRequest){
        return productRepository.findAllByMarkForDeleteIsFalse(queryRequest.getCategory(),
                queryRequest.getSearchKeyword(),
                PageRequest.of(queryRequest.getPage(),
                        queryRequest.getSize(),
                        Sort.by(queryRequest.getSort_by().equals("") ?"createdBy":queryRequest.getSort_by()).ascending()));
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    @Override
    public Product getProductById(String id){
        return productRepository.findByIdAndMarkForDeleteIsFalse(id).orElseThrow(() -> new NotFoundException("product is not found"));
    }

    @SneakyThrows
    @Transactional
    @Override
    public void deleteProductById (String id){
        Product product = productRepository.findByIdAndMarkForDeleteIsFalse(id).orElseThrow(() -> new NotFoundException("product is not found"));
        product.setMarkForDelete(true);
        product.setDeletedDate(new Date());
        productRepository.save(product);
    }

    @SneakyThrows
    @Transactional
    @Override
    public Product updateProduct (Product updatedProduct){
        Product product = getProductById(updatedProduct.getId());
        ProductCategory productCategory = productCategoryRepository.findByIdAndMarkForDeleteIsFalse(updatedProduct.getProductCategory().getId()).orElseThrow(() -> new NotFoundException("product category is not found"));

        product.setProductCategory(productCategory);
        product.setName(updatedProduct.getName());
        product.setBrand(updatedProduct.getBrand());
        product.setQuantityInStock(updatedProduct.getQuantityInStock());
        product.setCode(updatedProduct.getCode());
        product.setProductSeries(updatedProduct.getProductSeries());
        product.setSize(updatedProduct.getSize());
        product.setNetWeight(updatedProduct.getNetWeight());
        product.setPrice(updatedProduct.getPrice());
        product.setImage(updatedProduct.getImage());
        product.setExpDate(updatedProduct.getExpDate());

        productRepository.save(product);
        return product;
    }

    @SneakyThrows
    @Transactional
    @Override
    public Product updateProductImage (Product updatedProduct){
        Product product = getProductById(updatedProduct.getId());
        product.setImage(updatedProduct.getImage());

        productRepository.save(product);
        return product;
    }
}
