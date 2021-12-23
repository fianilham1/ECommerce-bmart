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
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart", schema = "public")
@Entity
public class Cart extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "fk_users_id", nullable = false)
    private Users users;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy="cart", cascade = CascadeType.ALL)
    @Where(clause = "mark_for_delete = false")
    private List<CartItems> cartItemsList;

    @Column(name = "total_products_price")
    private Double totalProductsPrice;

}
