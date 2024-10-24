package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tradeitem")
public class Trade_Item implements Serializable {

    @Id
    @OneToOne
    @JoinColumn (name = "t_num", referencedColumnName = "t_num", unique = true, columnDefinition = "SMALLINT DEFAULT NULL")
    private Trade trade;

    @Column(name = "t_item", length = 50, columnDefinition = "VARCHAR(50) DEFAULT NULL")
    private String tradeItem_item;

    @Column(name = "t_itemcount", columnDefinition = "SMALLINT DEFAULT NULL")
    private int tradeItem_count;

    @Column(name = "t_unit", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 9")
    private String tradeItem_unit;

    @Column(name = "t_change", length = 50, columnDefinition = "VARCHAR(50) DEFAULT NULL")
    private String tradeItem_change;

    @Column(name = "t_order", columnDefinition = "SMALLINT DEFAULT NULL")
    private int tradeItem_order;
}
