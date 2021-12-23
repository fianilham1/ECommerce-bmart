package com.tes.buana.entity;

import com.tes.buana.entity.auditable.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category", schema = "public")
@Entity
public class ProductCategory extends BaseModel {

    @Column(name = "product_category_name", nullable = false)
    private String name;

}
