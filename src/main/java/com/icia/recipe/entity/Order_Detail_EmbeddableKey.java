package com.icia.recipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Order_Detail_EmbeddableKey {

    @Column(name = "o_num", nullable = false, unique = true)
    private int orderDetail_FK_orderNum;

    @Column(name = "f_num", nullable = false, unique = true)
    private int orderDetail_FK_FooditemNum;


    public Order_Detail_EmbeddableKey (int orderDetail_FK_orderNum, int orderDetail_FK_FooditemNum) {
        this.orderDetail_FK_orderNum = orderDetail_FK_orderNum;
        this.orderDetail_FK_FooditemNum = orderDetail_FK_FooditemNum;
    }

}
