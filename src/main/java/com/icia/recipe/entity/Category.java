package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "c_num", nullable = false)
    private String bigCgNum;

    @Column(name = "c_num2", nullable = true, columnDefinition = "VARCHAR(10) DEFAULT NULL")
    private String midCgNum;

    @Column(name = "c_name", nullable = false)
    private String category_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_num2", referencedColumnName = "c_num")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_num2", referencedColumnName = "c_num")
    private Category category2;

    @OneToMany(mappedBy = "bigCgNum")
    private List<FoodItem> foodItems = new ArrayList<FoodItem>();

    @ManyToMany(mappedBy = "tradeCg")
    private List<Trade> tradeList = new ArrayList<>();

}
