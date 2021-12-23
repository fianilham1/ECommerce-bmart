package com.tes.buana.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tes.buana.entity.auditable.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items", schema = "public")
@Entity
public class CartItems extends BaseModel {

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "fk_cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "fk_product_id", nullable = false)
    private Product product;

    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;

    @Column(name = "product_price", nullable = false)
    private Double productPrice;

}
