package com.tes.buana.common.converter;

import com.tes.buana.common.util.MapperUtil;
import com.tes.buana.dto.product_category.ProductCategoryDto;
import com.tes.buana.entity.ProductCategory;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryConverter {
    public ProductCategoryDto toDto(ProductCategory productCategory){
        return MapperUtil.parse(productCategory, ProductCategoryDto.class);
    }

    public ProductCategory toEntity(ProductCategoryDto productCategoryDto){
        return MapperUtil.parse(productCategoryDto, ProductCategory.class);
    }
}

