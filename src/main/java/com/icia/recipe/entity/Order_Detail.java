package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orderdetail")
public class Order_Detail {

    @EmbeddedId
    private Order_Detail_EmbeddableKey orderDetailEmbeddableKey;

    @MapsId("o_num")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "o_num")
    private Order order;

    @MapsId("f_num")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_num")
    private FoodItem foodItem;

    @Column(name = "o_unit", unique = true, columnDefinition = "SMALLINT DEFAULT NULL")
    private int order_detail_unit;
}
