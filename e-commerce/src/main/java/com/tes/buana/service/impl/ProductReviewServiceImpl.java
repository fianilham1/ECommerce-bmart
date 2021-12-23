package com.tes.buana.service.impl;



import com.tes.buana.common.exception.NotFoundException;
import com.tes.buana.config.JwtUtil;
import com.tes.buana.entity.Product;

import com.tes.buana.entity.ProductReview;
import com.tes.buana.entity.Users;
import com.tes.buana.entity.web_query.ProductReviewParamsQuery;
import com.tes.buana.repository.ProductRepository;
import com.tes.buana.repository.ProductReviewRepository;
import com.tes.buana.service.ProductReviewService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @SneakyThrows
    @Transactional
    @Override
    public ProductReview addProductReview(ProductReview productReview, String productId, Users users){
        Product existingProduct = productRepository.findByIdAndMarkForDeleteIsFalse(productId)
                .orElseThrow(() -> new NotFoundException("product is not found"));
        productReview.setUsers(users);
        productReview.setProduct(existingProduct);
        productReviewRepository.save(productReview);
        return productReview;
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    @Override
    public Page<ProductReview> getAllProductReview(ProductReviewParamsQuery queryRequest){
        return productReviewRepository.findAllByMarkForDeleteIsFalse(
                PageRequest.of(queryRequest.getPage(),
                        queryRequest.getSize(),
                        Sort.by(queryRequest.getSort_by().equals("")?"createdBy":queryRequest.getSort_by()).ascending()));
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    @Override
    public Page<ProductReview> getAllProductReviewByProduct(ProductReviewParamsQuery queryRequest, Product product){
        return productReviewRepository.findAllByProductMarkForDeleteIsFalse(
                PageRequest.of(queryRequest.getPage(),
                        queryRequest.getSize(),
                        Sort.by(queryRequest.getSort_by().equals("")?"createdBy":queryRequest.getSort_by()).ascending()), product);
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    @Override
    public Page<ProductReview> getAllProductReviewByUsers(ProductReviewParamsQuery queryRequest, Users users){
        return productReviewRepository.findAllByUsersMarkForDeleteIsFalse(
                PageRequest.of(queryRequest.getPage(),
                        queryRequest.getSize(),
                        Sort.by(queryRequest.getSort_by().equals("")?"createdBy":queryRequest.getSort_by()).ascending()), users);

    }

}
