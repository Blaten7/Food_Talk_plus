package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orderdetail")
public class Order_Detail implements Serializable {

    @EmbeddedId
    private Order_Detail_EmbeddableKey orderDetailEmbeddableKey;

    @MapsId("orderDetail_FK_orderNum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "o_num")
    private Order order;

    @MapsId("orderDetail_FK_FooditemNum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_num")
    private FoodItem foodItem;

    @Column(name = "o_unit", unique = true, columnDefinition = "SMALLINT DEFAULT NULL")
    private int order_detail_unit;
}
