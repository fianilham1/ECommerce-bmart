package com.tes.buana.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tes.buana.entity.auditable.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_stock", schema = "public")
@Entity
public class Product extends BaseModel {

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_brand", nullable = false)
    private String brand;

    @Column(name = "product_quantity_in_stock", nullable = false)
    private Integer quantityInStock;

    @Column(name = "product_code", nullable = false)
    private String code;

    @Column(name = "product_series")
    private String productSeries;

    @Column(name = "product_size")
    private String size;

    @Column(name = "product_net_weight")
    private Integer netWeight;

    @Column(name = "product_price", nullable = false)
    private Double price;

    @Column(name = "product_image")
    private String image;

    @Column(name = "product_exp_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expDate;

    @Column(name = "product_ready_status", nullable = false)
    private boolean readyStatus;

    @ManyToOne
    @JoinColumn(name = "fk_product_category_id", nullable = false)
    private ProductCategory productCategory;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy="product", cascade = CascadeType.ALL)
    @Where(clause = "mark_for_delete = false")
    private List<ProductReview> productReviewList;

}
