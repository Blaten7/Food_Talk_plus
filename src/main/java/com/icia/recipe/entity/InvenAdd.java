package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "invenadd")
public class InvenAdd implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iv_num", nullable = false, unique = true)
    private String inven_add_num;

    @Column(name = "iv_company", columnDefinition = "VARCHAR(30) DEFAULT NULL", length = 30)
    private String inven_company;

    @Column(name = "iv_name", columnDefinition = "VARCHAR(30) DEFAULT NULL", length = 30)
    private String inven_name;

    @Column(name = "iv_count", columnDefinition = "INT DEFAULT NULL", unique = true)
    private int inven_count;

    @Column(name = "iv_price", columnDefinition = "INT DEFAULT NULL", unique = true)
    private int inven_price;

    @Column(name = "iv_vat", columnDefinition = "DOUBLE DEFAULT NULL", unique = true)
    private double inven_vat;

    @Column(name = "iv_total", columnDefinition = "INT DEFAULT NULL", unique = true)
    private int inven_total;

    @Column(name = "iv_current", columnDefinition = "VARCHAR(30) DEFAULT '발주 전'")
    private String inven_current;

}
