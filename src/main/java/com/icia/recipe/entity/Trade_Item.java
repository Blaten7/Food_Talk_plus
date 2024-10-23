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
@Table(name = "tradeitem")
public class Trade_Item {

    @Id
    @Column(name = "t_num", unique = true, columnDefinition = "SMALLINT DEFAULT NULL")
    private int tradeItem_num;

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
