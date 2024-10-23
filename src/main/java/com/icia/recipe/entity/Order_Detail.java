package com.icia.recipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orderdetail")
public class Order_Detail {

    @Id
    @Column(name = "o_num", nullable = false, unique = true)
    private int orderDetail_FK_orderNum;

    @Column(name = "f_num", nullable = false, unique = true)
    private int orderDetail_FK_FooditemNum;

    @Column(name = "o_unit", unique = true, columnDefinition = "SMALLINT DEFAULT NULL")
    private int order_detail_unit;
}
