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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_review", schema = "public")
@Entity
public class ProductReview extends BaseModel {

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "fk_product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "fk_users_id", nullable = false)
    private Users users;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "comment", nullable = false)
    private String comment;

}
