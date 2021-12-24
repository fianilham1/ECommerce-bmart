package com.tes.buana.service.impl;


import com.tes.buana.common.exception.NotFoundException;
import com.tes.buana.common.util.SortDirectionUtil;
import com.tes.buana.config.JwtUtil;
import com.tes.buana.entity.ProductCategory;
import com.tes.buana.entity.web_query.ProductCategoryParamsQuery;
import com.tes.buana.repository.ProductCategoryRepository;
import com.tes.buana.service.ProductCategoryService;
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

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @SneakyThrows
    @Transactional
    @Override
    public List<ProductCategory> addProductCategories(List<ProductCategory> productCategoryList){
        for(ProductCategory productCategory : productCategoryList){
            Optional<ProductCategory> category = productCategoryRepository.findByNameAndMarkForDeleteIsFalse(productCategory.getName());
            if(category.isPresent()){
                throw new Exception("product category name already exist");
            }
        }
        productCategoryRepository.saveAll(productCategoryList);
        return productCategoryList;
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    @Override
    public Page<ProductCategory> getAllProductCategory(ProductCategoryParamsQuery queryRequest){
        String sortBy = queryRequest.getSort_by().equals("")?"createdDate":queryRequest.getSort_by();
        String direction = queryRequest.getDirection().equals("")?"asc":queryRequest.getDirection();
        return productCategoryRepository.findAllByMarkForDeleteIsFalse(
                PageRequest.of(queryRequest.getPage(),
                        queryRequest.getSize(),
                        Sort.by(SortDirectionUtil.getDirection(direction),sortBy)));
    }

    @SneakyThrows
    @Transactional
    @Override
    public ProductCategory getProductCategory(String id){
        return productCategoryRepository.findByIdAndMarkForDeleteIsFalse(id).orElseThrow(() -> new NotFoundException("product category is not found"));

    }

    @SneakyThrows
    @Transactional
    @Override
    public void deleteProductCategory (String id){
        ProductCategory productCategory = productCategoryRepository.findByIdAndMarkForDeleteIsFalse(id).orElseThrow(() -> new NotFoundException("product category is not found"));
        productCategory.setMarkForDelete(true);
        productCategory.setDeletedDate(new Date());
        productCategoryRepository.save(productCategory);
    }

    @SneakyThrows
    @Transactional
    @Override
    public ProductCategory updateProductCategory (ProductCategory updatedProductCategory) {
        ProductCategory productCategory = getProductCategory(updatedProductCategory.getId());
        productCategory.setName(updatedProductCategory.getName());
        productCategoryRepository.save(productCategory);
        return productCategory;
    }


}
