package com.tes.buana.service.impl;

import com.tes.buana.common.exception.NotFoundException;
import com.tes.buana.common.util.SortDirectionUtil;
import com.tes.buana.entity.Product;
import com.tes.buana.entity.ProductCategory;
import com.tes.buana.entity.ProductReview;
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
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<Product> existingProductList = productRepository.findAllByMarkForDeleteIsFalse("","",null).stream().collect(Collectors.toList());

        for (Product product : productList) {
            ProductCategory productCategory = productCategoryRepository.findByIdAndMarkForDeleteIsFalse(product.getProductCategory().getId()).orElseThrow(() -> new NotFoundException("product category is not found"));
            product.setProductCategory(productCategory);
            product.setReadyStatus(true);
            //check the exact same name
            Optional<Product> existingProduct = existingProductList.stream()
                    .filter(p -> p.getName().equals(product.getName()))
                    .findFirst();
            if(existingProduct.isPresent()){
                throw new Exception("product's name already exist");
            }
        }

        productRepository.saveAll(productList);
        return productList;
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    @Override
    public Page<Product> getAllProduct(ProductParamsQuery queryRequest){
        String sortBy = queryRequest.getSort_by().equals("")?"createdDate":queryRequest.getSort_by();
        String direction = queryRequest.getDirection().equals("")?"asc":queryRequest.getDirection();
        return productRepository.findAllByMarkForDeleteIsFalse(queryRequest.getCategory(),
                queryRequest.getSearchKeyword(),
                PageRequest.of(queryRequest.getPage(),
                        queryRequest.getSize(),
                        Sort.by(SortDirectionUtil.getDirection(direction),sortBy)));
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
        product.setDescription(updatedProduct.getDescription());

        Double ratingAverage = product.getProductReviewList().stream().mapToDouble(ProductReview::getRating).sum()/product.getProductReviewList().size();
        product.setRating(ratingAverage);

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
