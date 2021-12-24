package com.tes.buana.common.converter;

import com.tes.buana.common.util.MapperUtil;
import com.tes.buana.dto.product.ProductDto;
import com.tes.buana.dto.product_review.ProductReviewDto;
import com.tes.buana.entity.CartItems;
import com.tes.buana.entity.Product;
import com.tes.buana.entity.ProductReview;
import com.tes.buana.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ProductConverter {
    public ProductDto toDto(Product product){
        ProductDto productDto = MapperUtil.parse(product, ProductDto.class);
        productDto.setName(productDto.getName().replace('_', ' '));
        if(!ObjectUtils.isEmpty(product.getProductReviewList())){
            for(int i=0;i<productDto.getProductReviewList().size();i++){
                productDto.getProductReviewList().get(i).setUserName(product.getProductReviewList().get(i).getUsers().getName());
                productDto.getProductReviewList().get(i).setUserImage(product.getProductReviewList().get(i).getUsers().getImage());
            }
        }
        return productDto;
    }

    public Product toEntity(ProductDto productDto){
        return MapperUtil.parse(productDto, Product.class);
    }
}

//ProductDto.builder()
//        .name(product.getName())
//        .brand(product.getBrand())
//        .code(product.getCode())
//        .quantityInStock(product.getQuantityInStock())
//        .productSeries(product.getProductSeries())
//        .size(product.getSize())
//        .netWeight(product.getNetWeight())
//        .price(product.getPrice())
//        .image(product.getImage())
//        .expDate(product.getExpDate())
//        .productCategory(product.getProductCategory().getName())
//        .build()
