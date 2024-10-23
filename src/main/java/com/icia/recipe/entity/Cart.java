package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ca_num", nullable = false, unique = true)
    private int cart_num;

    @Column(name = "m_id", unique = true, length = 20)
    private String memberId_FK_Cart;

    @Column(name = "f_num", unique = true)
    private int f_num;

    @Column(name = "ca_count", length = 10, columnDefinition = "VARCHAR(10) DEFAULT NULL")
    private String ca_count;

    @Column(name = "ca_date", columnDefinition = "DATE DEFAULT NOW()")
    private Date ca_date;
}
