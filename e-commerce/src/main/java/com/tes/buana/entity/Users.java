package com.tes.buana.entity;

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
@Table(name = "users", schema = "public")
@Entity
public class Users extends BaseModel {

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "names")
    private String name;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "user_image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "fk_role_id", nullable = false)
    private Role role;

}
