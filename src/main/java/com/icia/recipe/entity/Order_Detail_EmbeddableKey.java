package com.icia.recipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Order_Detail_EmbeddableKey implements Serializable {

    @Column(name = "o_num", nullable = false, unique = true)
    private Long orderDetail_FK_orderNum;

    @Column(name = "f_num", nullable = false, unique = true)
    private Long orderDetail_FK_FooditemNum;


    public Order_Detail_EmbeddableKey (Long orderDetail_FK_orderNum, Long orderDetail_FK_FooditemNum) {
        this.orderDetail_FK_orderNum = orderDetail_FK_orderNum;
        this.orderDetail_FK_FooditemNum = orderDetail_FK_FooditemNum;
    }

}
